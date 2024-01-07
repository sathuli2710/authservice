package com.codelad.authservice.dtos;

import com.codelad.authservice.utils.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer uid;
    @NotBlank(message = "username should be passed")
    @NotNull(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "email should be passed")
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotBlank(message = "password should be passed")
    @NotNull(message = "password cannot be empty")
    @JsonIgnore
    private String password;
    @NotBlank(message = "firstName should be passed")
    @NotNull(message = "firstName cannot be empty")
    private String firstName;
    private String middleName;
    @NotBlank(message = "lastName should be passed")
    @NotNull(message = "lastName cannot be empty")
    private String lastName;
    @NotBlank(message = "phoneNumber should be passed")
    @NotNull(message = "phoneNumber cannot be empty")
    private String phoneNumber;
    private VerificationStatus verificationStatus;
}
