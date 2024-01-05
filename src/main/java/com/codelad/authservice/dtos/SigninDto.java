package com.codelad.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninDto {
    @NotBlank(message = "username should be passed")
    @NotNull(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "username should be passed")
    @NotNull(message = "username cannot be empty")
    @JsonIgnore
    private String password;
}
