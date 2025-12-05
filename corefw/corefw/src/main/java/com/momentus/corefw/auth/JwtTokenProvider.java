package com.momentus.corefw.auth;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final JwtProperties props;
    private Key hmacKey;

    public JwtTokenProvider(JwtProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init() {
        // Use HMAC-SHA algorithm key derived from secret
        byte[] keyBytes = props.getSecret().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        // If secret is short, Keys.hmacShaKeyFor will still accept but prefer long secure secret
        this.hmacKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Create JWT with subject (username) and optional roles.
     */
    public String createToken(String subject, Collection<String> roles) {
        long now = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + props.getExpiration() * 1000))
                .signWith(hmacKey, SignatureAlgorithm.HS256);

        if (roles != null && !roles.isEmpty()) {
            builder.claim("roles", roles);
        }
        return builder.compact();
    }

    /**
     * Validate token (signature + expiration). Throws runtime exceptions on invalid tokens.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // invalid token (expired, tampered, unsupported, etc.)
            return false;
        }
    }

    /**
     * Parse claims and return username
     */
    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extract roles (if present) from token; empty list if none.
     */
    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Object roles = claims.get("roles");
        if (roles instanceof Collection) {
            return ((Collection<?>) roles).stream().map(Object::toString).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
