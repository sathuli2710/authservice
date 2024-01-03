package com.codelad.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDto<T> {
    private int statusCode;
    private String message;
    private T data;
    private T error;
}
