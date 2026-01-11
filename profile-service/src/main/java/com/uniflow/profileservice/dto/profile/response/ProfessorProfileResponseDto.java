package com.uniflow.profileservice.dto.profile.response;

import com.uniflow.profileservice.dto.profile.intr.OwnProfileResponseDto;
import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfessorProfileResponseDto implements OwnProfileResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
    private Title title;
    private Role role;
}
