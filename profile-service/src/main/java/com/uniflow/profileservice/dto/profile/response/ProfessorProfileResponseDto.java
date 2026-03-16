package com.uniflow.profileservice.dto.profile.response;

import com.uniflow.profileservice.dto.profile.intr.ProfileResponseDto;
import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.enums.AcademicTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfessorProfileResponseDto implements ProfileResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
    private AcademicTitle academicTitle;
    private Role role;
}
