package com.uniflow.academicservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SpecializationCreateDto {
    private String specializationName;
    private String facultyName;
}
