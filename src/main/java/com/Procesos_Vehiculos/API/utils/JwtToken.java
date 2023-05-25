package com.Procesos_Vehiculos.API.utils;

import com.Procesos_Vehiculos.API.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;

/**
 * Class to create the token and extract its data.
 */
public class JwtToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtToken.class);
    private static final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 3600L;

    private JwtToken () {}

    public static String generateToken(User user) {
        //Token expiration time
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000L;
        //Token expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        //User email
        String email = user.getEmail();
        Long id = user.getId();

        //Set subject email in JWT
        Claims claims = Jwts.claims().setSubject(email);
        //Set the role in JWT
        claims.put("id", id);

        //Token generation and return
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    /**
     * Method to obtain token credentials.
     * @param token JWT
     * @return User authentication
     */
    public static UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            //Extract the email from the token
            String email = claims.getSubject();
            //Return a new User authentication with user credentials
            return new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    null
            );
        }
        catch (JwtException e) {
            return null;
        }
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("Malformed token");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported token");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired token");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Empty token");
        } catch (SignatureException e) {
            LOGGER.error("Signature failure");
        }
        return false;
    }

}