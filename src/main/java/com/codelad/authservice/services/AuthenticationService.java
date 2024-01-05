package com.codelad.authservice.services;

import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;

public interface AuthenticationService {
    String signup(UserDto userDto) throws Exception ;
    String signin(SigninDto signinDto);
}
