package com.uniflow.identity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestUserDto {
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+$", message = "Field must contain only letters")
        private String firstName;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+$", message = "Field must contain only letters")
        private String lastName;
        @NotBlank
        private String username;
        @Email
        @NotBlank
        private String email;
        @Size(min = 8, message = "Password should be bigger than 8 symbols!")
        private String password;
        @Min(value = 18, message = "Age should not be less than 18!")
        private int age;
        @NotBlank
        @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
        private String phoneNumber;
    }
