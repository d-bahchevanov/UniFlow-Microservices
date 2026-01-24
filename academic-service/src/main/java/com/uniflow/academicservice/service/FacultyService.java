package com.uniflow.academicservice.service;

import com.uniflow.academicservice.dto.FacultyResponseDto;
import com.uniflow.academicservice.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<FacultyResponseDto> getAllFaculties();
    FacultyResponseDto createFaculty(String name);
    FacultyResponseDto getFacultyByName(String name);
}

