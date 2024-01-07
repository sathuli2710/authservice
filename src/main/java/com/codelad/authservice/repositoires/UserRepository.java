package com.codelad.authservice.repositoires;

import com.codelad.authservice.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUid(Long uid);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    @Query(value = "SELECT nextval('users_inner_seq')", nativeQuery = true)
    int getInnerSequenceNextValue();
}
