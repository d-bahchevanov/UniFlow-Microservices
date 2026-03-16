package com.uniflow.profileservice.dto.update.response;

import com.uniflow.profileservice.enums.AcademicTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminUpdateResponseDto {
    private String faculty;
    private String specialization;
    private int yearOfStudy;
    private AcademicTitle academicTitle;
}
