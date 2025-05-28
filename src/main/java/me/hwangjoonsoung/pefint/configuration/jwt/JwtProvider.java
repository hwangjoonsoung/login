package me.hwangjoonsoung.pefint.configuration.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    private final long expirationSecond = 1000 * 60 * 60;

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationSecond);

        JwtBuilder jwt = Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate).signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)));
        return jwt.compact();
    }

    public String getEmailFromToken(String token) {
        String email = Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJwt(token).getBody().getSubject();
        return email;
    }

    public boolean vaildationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJwt(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
