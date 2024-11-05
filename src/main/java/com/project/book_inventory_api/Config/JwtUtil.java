package com.project.book_inventory_api.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

        private final String SECRET_KEY = "your_secret_key"; // Used to sign and verify JWTs

        // Generates a JWT with a username and expiration time
        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        // Extracts the username from a given JWT
        public String extractUsername(String token) {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        }

        // Checks if the token is expired
        private boolean isTokenExpired(String token) {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        }

        // Validates the token by checking the username and expiration
        public boolean validateToken(String token, String username) {
            return username.equals(extractUsername(token)) && !isTokenExpired(token);
        }
    }

