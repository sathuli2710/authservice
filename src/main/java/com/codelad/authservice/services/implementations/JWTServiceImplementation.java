package com.codelad.authservice.services.implementations;

import com.codelad.authservice.ApplicationConstants;
import com.codelad.authservice.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImplementation implements JWTService {
    private final Logger logger = LoggerFactory.getLogger(JWTServiceImplementation.class);

    @Value("${spring.token.signing.key}")
    private String jwtTokenSigningKey;

    private Key getJwtSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtTokenSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateJwtTokenWithClaims(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().claims(claims).subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + ApplicationConstants.JWT_ACCESS_TOKEN_EXPIRY_MILLI_SECONDS)).signWith(getJwtSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getJwtSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails){
        return generateJwtTokenWithClaims(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String uidFromToken = extractUsername(token);
        return uidFromToken.equals(userDetails.getUsername()) && !isExpired(token);
    }

}
