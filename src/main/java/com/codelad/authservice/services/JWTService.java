package com.codelad.authservice.services;

import org.springframework.security.core.userdetails.UserDetails;


public interface JWTService {
    public String extractUsername(String token);
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
}
