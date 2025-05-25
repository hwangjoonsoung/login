package me.hwangjoonsoung.pefint.configuration.jwt;

import io.jsonwebtoken.JwtBuilder;
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

}
