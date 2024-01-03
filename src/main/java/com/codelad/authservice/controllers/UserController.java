package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api/v1/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<? extends GenericResponseDto<?>> createUser(@Validated @ModelAttribute UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(ObjectError error: bindingResult.getAllErrors()){
                errorMap.put(error.getObjectName(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Error creating user", null, errorMap), HttpStatus.BAD_REQUEST);
        }
        try{
            UserEntity createdUserEntity = userService.createUser(userDto);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.CREATED.value(), "Successfully created the user", createdUserEntity.getUid(), null), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Error creating user", null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
