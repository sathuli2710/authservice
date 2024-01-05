package com.codelad.authservice.services.implementations;

import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.services.AuthenticationService;
import com.codelad.authservice.services.JWTService;
import com.codelad.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public String signup(UserDto userDto) throws Exception {
        UserEntity userEntity = userService.createUser(userDto);
        return jwtService.generateToken(userEntity);
    }
    @Override
    public String signin(SigninDto signinDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinDto.getUsername(), signinDto.getPassword()));
        UserEntity userEntity = userService.getUserByUsername(signinDto.getUsername());
        return jwtService.generateToken(userEntity);
    }
}
