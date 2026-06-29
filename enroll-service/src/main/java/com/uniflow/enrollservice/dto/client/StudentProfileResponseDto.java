package com.uniflow.enrollservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentProfileResponseDto {
    private Long studentId;
    private Long facultyId;
    private Long specializationId;
    private String subjectName;
    private int year;
}
