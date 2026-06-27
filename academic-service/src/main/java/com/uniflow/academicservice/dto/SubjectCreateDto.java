package com.uniflow.academicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubjectCreateDto {
    String specializationName;
    String subjectName;
    int year;
}
