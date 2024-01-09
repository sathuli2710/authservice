package com.codelad.authservice.controllers;

import com.codelad.authservice.dtos.GenericResponseDto;
import com.codelad.authservice.entities.RefreshTokenEntity;
import com.codelad.authservice.services.RefreshTokenService;
import com.codelad.authservice.utils.GlobalUtils;
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

    @Autowired
    GlobalUtils globalUtils;

    @GetMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> getRefreshTokenByUsername(@NotNull @PathVariable String username){
        try{
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByUsername(username);
            return globalUtils.generateSuccessResponse(HttpStatus.OK, refreshTokenEntity.get());
        }
        catch (NoSuchElementException e){
            return globalUtils.generateErrorResponse(HttpStatus.NOT_FOUND, "This user does not have any refresh token");
        }
        catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("isvalid/")
    public ResponseEntity<GenericResponseDto<?>> validateByToken(@NotNull @RequestParam String token){
        try {
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByToken(token);
            boolean isValid = refreshTokenService.isValidRefreshToken(refreshTokenEntity.get());
            if(isValid){
                return globalUtils.generateSuccessResponse(HttpStatus.OK, "It is a valid token");
            }else{
                return globalUtils.generateErrorResponse(HttpStatus.NOT_FOUND, "Token is expired/not_valid");
            }
        }
        catch (NoSuchElementException e){
            return globalUtils.generateErrorResponse(HttpStatus.NOT_FOUND, "Token does not exists");
        }
        catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> createTokenByUsername(@NotNull @PathVariable String username){
        try {
            RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(username);
            return globalUtils.generateSuccessResponse(HttpStatus.CREATED, refreshTokenEntity);
        }
        catch (DataIntegrityViolationException e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST,  "Cannot create multiple refresh tokens for the same user");
        }
        catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity<GenericResponseDto<?>> deleteTokenByUsername(@NotNull @PathVariable String username){
        try {
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenService.findByUsername(username);
           if(refreshTokenEntity.isPresent()){
               refreshTokenService.deleteByUsername(username);
               return globalUtils.generateSuccessResponse(HttpStatus.OK, "Token is deleted successfully");
           }
           else{
               return globalUtils.generateErrorResponse(HttpStatus.NOT_FOUND,  "There is not refresh token for this username");
           }
        }catch (Exception e) {
            return globalUtils.generateErrorResponse(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }
}