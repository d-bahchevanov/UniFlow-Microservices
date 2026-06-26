package com.uniflow.enrollservice.dto;

import com.uniflow.enrollservice.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollmentResponseDto {
    private String facultyName;
    private String specializationName;
    private String subjectName;
    private int semester;
    private EnrollmentStatus status;
}
