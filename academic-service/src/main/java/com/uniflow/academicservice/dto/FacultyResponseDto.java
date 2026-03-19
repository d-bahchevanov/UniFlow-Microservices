package com.uniflow.academicservice.dto;
import com.uniflow.academicservice.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FacultyResponseDto {
    private String name;
    private List<DomainNameDto> specializationList;
}
