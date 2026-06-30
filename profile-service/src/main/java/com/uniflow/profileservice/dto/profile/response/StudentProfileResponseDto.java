package com.uniflow.profileservice.dto.profile.response;

import com.uniflow.profileservice.dto.profile.intr.ProfileResponseDto;
import com.uniflow.profileservice.enums.AcademicTitle;
import com.uniflow.profileservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentProfileResponseDto implements ProfileResponseDto {
    private Long studentId;
    private String username;
    private String firstName;
    private String lastName;
    private long facultyId;
    private long specializationId;
    private int yearOfStudy;
    private Role role;
    private AcademicTitle academicTitle;
}
