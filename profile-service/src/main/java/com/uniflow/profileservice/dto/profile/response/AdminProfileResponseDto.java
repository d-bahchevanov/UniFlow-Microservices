package com.uniflow.profileservice.dto.profile.response;

import com.uniflow.profileservice.dto.profile.intr.ProfileResponseDto;
import com.uniflow.profileservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminProfileResponseDto implements ProfileResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
}
