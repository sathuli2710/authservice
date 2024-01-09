package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.services.UserService;
import com.codelad.authservice.utils.UserUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserUtils userUtils;

    @GetMapping("{uid}")
    public ResponseEntity<GenericResponseDto<?>> getUserByUid(@NotNull @PathVariable String uid){
        try{
            UserEntity user = (UserEntity) userService.getUserByUid(Long.parseLong(uid));
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", userUtils.userEntityToUserDto(user), null), HttpStatus.OK);
        }catch(UsernameNotFoundException e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, "Couldn't find the user"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponseDto<?>> getUserByUsername(@NotNull @RequestParam String username){
        try{
            UserEntity user = (UserEntity) userService.getUserByUsername(username);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", userUtils.userEntityToUserDto(user), null), HttpStatus.OK);
        }catch(UsernameNotFoundException e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, "Couldn't find the user"), HttpStatus.BAD_REQUEST);
        }
    }
}
