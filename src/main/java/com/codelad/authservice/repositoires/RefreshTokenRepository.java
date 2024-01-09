package com.codelad.authservice.repositoires;

import com.codelad.authservice.entities.RefreshTokenEntity;
import com.codelad.authservice.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    @Modifying
    int deleteByUser(UserEntity user);
    Optional<RefreshTokenEntity> findByUser(UserEntity user);
}