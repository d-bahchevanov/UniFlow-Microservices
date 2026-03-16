package com.uniflow.profileservice.dto.profile.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileRequestDto {
    private String firstName;
    private String lastName;
}
