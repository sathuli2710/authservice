package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.AuthenticationResponse;
import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.dtos.SigninDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.services.AuthenticationService;
import com.codelad.authservice.utils.GlobalUtils;
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

    @Autowired
    GlobalUtils globalUtils;

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
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST, errorMap);
        }
        try{
            AuthenticationResponse authenticationResponse = authenticationService.signup(userDto);
            return globalUtils.generateSuccessResponse(HttpStatus.CREATED, authenticationResponse);
        } catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("signin")
    public ResponseEntity<GenericResponseDto<?>> signin(@Valid @ModelAttribute SigninDto signinDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(ObjectError error: bindingResult.getAllErrors()){
                errorMap.put(error.getObjectName(), error.getDefaultMessage());
            }
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST, errorMap);
        }
        try{
            AuthenticationResponse authenticationResponse = authenticationService.signin(signinDto);
            return globalUtils.generateSuccessResponse(HttpStatus.OK, authenticationResponse);
        } catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());        }
    }
}
