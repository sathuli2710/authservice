package com.codelad.authservice.services;

import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDetails getUserByUid(UUID uid) throws UsernameNotFoundException;
    UserEntity getUserByUsername(String username) throws UsernameNotFoundException;
    UserEntity createUser(UserDto userDto) throws Exception;
}
