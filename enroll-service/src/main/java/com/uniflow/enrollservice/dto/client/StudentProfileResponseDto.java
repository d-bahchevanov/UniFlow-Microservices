package com.uniflow.enrollservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentProfileResponseDto {
    private Long studentId;
    private String facultyName;
    private String specializationName;
    private String subjectName;
    private int yearOfStudy;
}
