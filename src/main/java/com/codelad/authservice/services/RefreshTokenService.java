package com.codelad.authservice.services;

import com.codelad.authservice.entities.RefreshTokenEntity;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUsername(String username);
    RefreshTokenEntity createRefreshToken(String username);
    boolean isValidRefreshToken(RefreshTokenEntity token);
    void deleteByUsername(String username) ;
}