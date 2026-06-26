package com.uniflow.enrollservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollmentRequestDto {
    private String facultyName;
    private String specializationName;
    private String subjectName;
    private int semester;
    //Need to make subject validation so student should only give subject name to enroll
}
