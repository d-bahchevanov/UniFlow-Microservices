package com.uniflow.enrollservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentProfileResponseDto {
    private Long studentId;
    private long facultyId;
    private long specializationId;
    private String subjectName;
    private int yearOfStudy;
}
