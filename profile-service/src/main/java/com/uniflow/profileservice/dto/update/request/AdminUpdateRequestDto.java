package com.uniflow.profileservice.dto.update.request;

import com.uniflow.profileservice.enums.AcademicTitle;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminUpdateRequestDto {
    private String facultyName;
    private String specializationName;
    //private Long facultyId;
    //private Long specializationId;
    @Min(value = 1)
    @Max(value = 6)
    private Integer yearOfStudy;
    private AcademicTitle academicTitle;
}
