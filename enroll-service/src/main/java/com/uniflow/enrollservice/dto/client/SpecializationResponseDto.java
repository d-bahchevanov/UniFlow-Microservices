package com.uniflow.enrollservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class SpecializationResponseDto {
        private String name;
        private String facultyName;
        private List<DomainNameDto> subjectsList;
}
