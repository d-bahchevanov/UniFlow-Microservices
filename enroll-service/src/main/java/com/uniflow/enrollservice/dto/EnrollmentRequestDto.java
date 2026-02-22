package com.uniflow.enrollservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollmentRequestDto {
    private long studentId;
    private String facultyName;
    private String specializationName;
    private String subjectName;
    private int semester;
}
