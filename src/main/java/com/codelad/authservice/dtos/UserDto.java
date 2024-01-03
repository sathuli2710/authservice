package com.codelad.authservice.dtos;

import com.codelad.authservice.utils.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private UUID uid;
    @NotBlank(message = "username should be passed")
    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "email should be passed")
    @NotBlank(message = "email cannot be empty")
    private String email;
    @NotBlank(message = "password should be passed")
    @NotBlank(message = "password cannot be empty")
    @JsonIgnore
    private String password;
    @NotBlank(message = "firstName should be passed")
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    private String middleName;
    @NotBlank(message = "lastName should be passed")
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotBlank(message = "phoneNumber should be passed")
    @NotBlank(message = "phoneNumber cannot be empty")
    private String phoneNumber;
    private VerificationStatus verificationStatus;
}
