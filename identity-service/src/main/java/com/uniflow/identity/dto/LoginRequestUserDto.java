package com.uniflow.identity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestUserDto {
    @NotBlank
    private String username;
    @Size(min = 8, message = "Password should be bigger than 8 symbols!")
    private String password;
}
