package com.uniflow.academicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FacultySpecializationValidationRequest {
    private Long facultyId;
    private Long specializationId;
}
