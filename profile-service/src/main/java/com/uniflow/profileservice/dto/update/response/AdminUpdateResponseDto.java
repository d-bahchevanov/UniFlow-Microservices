package com.uniflow.profileservice.dto.update.response;

import com.uniflow.profileservice.enums.AcademicTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminUpdateResponseDto {
    private Long facultyId;
    private Long specializationId;
    private int yearOfStudy;
    private AcademicTitle academicTitle;
}
