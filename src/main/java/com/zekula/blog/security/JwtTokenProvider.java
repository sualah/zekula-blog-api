package com.zekula.blog.security;

import com.zekula.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
       String username =  authentication.getName();
       Date currentDate = new Date();
       Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMs);

       JWT
        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(token).getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
         try {
             Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(token);

             return true;
         } catch (SignatureException ex) {

             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
         } catch (MalformedJwtException ex) {

             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
         } catch (ExpiredJwtException ex) {

             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
         } catch (UnsupportedJwtException ex) {

             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
         } catch (IllegalArgumentException ex) {

             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
         }
    }
}
