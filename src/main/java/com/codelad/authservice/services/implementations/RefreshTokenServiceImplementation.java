package com.codelad.authservice.services.implementations;

import com.codelad.authservice.ApplicationConstants;
import com.codelad.authservice.entities.RefreshTokenEntity;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.repositoires.RefreshTokenRepository;
import com.codelad.authservice.repositoires.UserRepository;
import com.codelad.authservice.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImplementation implements RefreshTokenService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public Optional<RefreshTokenEntity> findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
                return refreshTokenRepository.findByUser(userEntity.get());
        } else {
            throw new UsernameNotFoundException("Username does not exists");
        }
    }

    @Override
    public RefreshTokenEntity createRefreshToken(String username) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            refreshTokenEntity.setUser(userEntity.get());
            refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(ApplicationConstants.JWT_REFRESH_TOKEN_EXPIRY_MILLI_SECONDS));
            refreshTokenEntity.setToken(UUID.randomUUID().toString());
            refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
            return refreshTokenEntity;
        }else {
            throw new UsernameNotFoundException("Username does not exists");
        }
    }

    @Override
    public boolean isValidRefreshToken(RefreshTokenEntity token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(Objects.nonNull(userEntity)){
            int isDeleted = refreshTokenRepository.deleteByUser(userEntity.get());
            if(isDeleted == 0){
                throw new UsernameNotFoundException("Refresh token not deleted");
            }
        }else{
            throw new UsernameNotFoundException("Username does not exists");
        }
    }
}