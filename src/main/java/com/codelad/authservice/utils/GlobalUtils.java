package com.codelad.authservice.utils;

import com.codelad.authservice.dtos.GenericResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GlobalUtils {
    public <T> ResponseEntity<GenericResponseDto<?>> generateSuccessResponse(HttpStatus status, T data){
        return new ResponseEntity<>(GenericResponseDto.builder().statusCode(status.value()).message("SUCCESS").data(data).error(null).build(), status);
    }

    public <T> ResponseEntity<GenericResponseDto<?>> generateErrorResponse(HttpStatus status, T error){
        return new ResponseEntity<>(GenericResponseDto.builder().statusCode(status.value()).message("ERROR").data(null).error(error).build(), status);
    }
}
