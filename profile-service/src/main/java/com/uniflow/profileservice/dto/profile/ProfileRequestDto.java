package com.uniflow.profileservice.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
