package com.dominik.backend.Fibre.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWT {
    SecretKey secret;
    JWT(HushHush hushHush){
        secret = Keys.hmacShaKeyFor(hushHush.sekret);
    }

    public SecretKey getSignInKey() {
        return secret;
    }

    public String buildToken(
            Map<String, String> extraClaims,
            String subject
    ){
        return buildToken(extraClaims,subject,1000/*ms*/*60/*s*/*15/*m*/);//domyślny czas tokenu 15 minut
    }

    public String buildToken(
            Map<String, String> extraClaims,
            String subject,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Claims extractAllClaims(String token) {
        Object claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if((claims instanceof Claims))
            return (Claims) claims;


        throw new RuntimeException("invalid token format");
    }
}
