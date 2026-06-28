package com.uniflow.academicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubjectCreateDto {
    private String specializationName;
    private String subjectName;
    private int year;
}
