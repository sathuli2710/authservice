package com.codelad.authservice.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JWTService {
    public Long extractUid(String token);
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
}
