package me.hwangjoonsoung.pefint.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.service.RedisService;
import me.hwangjoonsoung.pefint.util.common.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Getter
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private RedisService redisService;

    private final long expirationSecond = 1000 * 60 * 30; //30분
//    private final long expirationSecond = 1000 * 20; //30초

    public String generateRefreshTokenByEmail(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationSecond);

        JwtBuilder jwt = Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate).signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)));
        return jwt.compact();
    }

    public String generateRefreshTokenById(Long id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationSecond);

        JwtBuilder jwt = Jwts.builder().setSubject(id.toString()).setIssuedAt(now).setExpiration(expiryDate).signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)));
        return jwt.compact();
    }

    public String getSubjectFromToken(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody().getSubject();
        return subject;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);

            String subject = this.getSubjectFromToken(token);
            String key = "user:" + subject;
            String value = redisService.get(key);

            CommonUtil commonUtil = new CommonUtil(new ObjectMapper());

            JsonNode jsonNode = commonUtil.stringToJson(value);
            if(jsonNode != null && jsonNode.get("token").toString().replaceAll("\"","").equals(token)){
                return true;
            }else{
                new JwtException("invalid Token");
                return false;
            }
        } catch (ExpiredJwtException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        } catch (JwtException e) {
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
