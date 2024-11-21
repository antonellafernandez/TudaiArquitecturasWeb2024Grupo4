package com.example.microservicio_usuario.providers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class TokenGenerator {

    private final SecretKey key;
    private final int tokenValidityInMilliseconds;

    public TokenGenerator(String secret, int validityInMilliseconds) {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = validityInMilliseconds;
    }

    public String generateArtificialToken(String username, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims) // Puedes agregar más datos aquí, como roles.
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}