package com.codelad.authservice.services.implementations;

import com.codelad.authservice.dtos.AuthenticationResponse;
import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.RefreshTokenEntity;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.services.AuthenticationService;
import com.codelad.authservice.services.JWTService;
import com.codelad.authservice.services.RefreshTokenService;
import com.codelad.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse signup(UserDto userDto) throws Exception {
        UserEntity userEntity = userService.createUser(userDto);
        return AuthenticationResponse.builder().accessToken(jwtService.generateToken(userEntity)).refreshToken(refreshTokenService.createRefreshToken(userEntity.getUsername()).getToken()).build();
    }
    @Override
    public AuthenticationResponse signin(SigninDto signinDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinDto.getUsername(), signinDto.getPassword()));
        UserEntity userEntity = userService.getUserByUsername(signinDto.getUsername());
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByUsername(userEntity.getUsername());
        if(refreshTokenEntity.isPresent()){
            return AuthenticationResponse.builder().accessToken(jwtService.generateToken(userEntity)).refreshToken(refreshTokenEntity.get().getToken()).build();
        }
        return AuthenticationResponse.builder().accessToken(jwtService.generateToken(userEntity)).refreshToken(refreshTokenService.createRefreshToken(userEntity.getUsername()).getToken()).build();
    }
}
