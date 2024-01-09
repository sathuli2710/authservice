package com.codelad.authservice.services;

import com.codelad.authservice.dtos.AuthenticationResponse;
import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;

public interface AuthenticationService {
    AuthenticationResponse signup(UserDto userDto) throws Exception ;
    AuthenticationResponse signin(SigninDto signinDto);
}
