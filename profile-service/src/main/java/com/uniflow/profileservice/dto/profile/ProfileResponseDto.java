package com.uniflow.profileservice.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
}
