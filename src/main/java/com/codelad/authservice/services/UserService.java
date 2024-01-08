package com.codelad.authservice.services;

import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDetails getUserByUid(Long uid) throws UsernameNotFoundException;
    UserEntity getUserByUsername(String username) throws UsernameNotFoundException;
    UserEntity createUser(UserDto userDto) throws Exception;
    List<UserDto> getAllUsers() throws Exception;
}
