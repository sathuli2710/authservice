package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.entities.RefreshTokenEntity;
import com.codelad.authservice.services.RefreshTokenService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth/refresh-token/")
public class RefreshTokenController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @GetMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> getRefreshTokenByUsername(@NotNull @PathVariable String username){
        try{
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByUsername(username);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", refreshTokenEntity.get(), null), HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.NOT_FOUND.value(), "ERROR", null, "This user does not have any refresh token"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.NOT_FOUND.value(), "ERROR", null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("isvalid/")
    public ResponseEntity<GenericResponseDto<?>> validateByToken(@NotNull @RequestParam String token){
        try {
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByToken(token);
            boolean isValid = refreshTokenService.isValidRefreshToken(refreshTokenEntity.get());
            if(isValid){
                return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", "It is a valid token", null), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.NOT_FOUND.value(), "ERROR", null, "Token is expired/not_valid"), HttpStatus.NOT_FOUND);
            }
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.NOT_FOUND.value(), "ERROR", null, "Token does not exists"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> createTokenByUsername(@NotNull @PathVariable String username){
        try {
            RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(username);
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", refreshTokenEntity, null), HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, "Cannot create multiple refresh tokens for the same user"), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> deleteTokenByUsername(@NotNull @PathVariable String username){
        try {
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByUsername(username);
           if(refreshTokenEntity.isPresent()){
               refreshTokenService.deleteByUsername(username);
               return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.OK.value(), "SUCCESS", "Token is deleted successfully", null), HttpStatus.OK);
           }
           else{
               return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.NOT_FOUND.value(), "ERROR", null, "There is not refresh token for this username"), HttpStatus.NOT_FOUND);
           }
        }catch (Exception e) {
            return new ResponseEntity<>(new GenericResponseDto<>(HttpStatus.BAD_REQUEST.value(), "ERROR", null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}