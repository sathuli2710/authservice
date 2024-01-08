package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.services.AuthenticationService;
import com.codelad.authservice.utils.StringToVerificationStatus;
import com.codelad.authservice.utils.VerificationStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(VerificationStatus.class, new StringToVerificationStatus());
    }

    @PostMapping("signup")
    public ResponseEntity<GenericResponseDto<?>> signup(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(ObjectError error: bindingResult.getAllErrors()){
                errorMap.put(error.getObjectName(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Error creating user", null, errorMap), HttpStatus.BAD_REQUEST);
        }
        try{
            String signupToken = authenticationService.signup(userDto);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.CREATED.value(), "Successfully created the user", signupToken, null), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Error creating user", null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<GenericResponseDto<?>> signin(@Valid @ModelAttribute SigninDto signinDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(ObjectError error: bindingResult.getAllErrors()){
                errorMap.put(error.getObjectName(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Error signing in", null, errorMap), HttpStatus.BAD_REQUEST);
        }
        try{
            String signinToken = authenticationService.signin(signinDto);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "Successfully signed in", signinToken, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.UNAUTHORIZED.value(), "Bad credentials", null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
