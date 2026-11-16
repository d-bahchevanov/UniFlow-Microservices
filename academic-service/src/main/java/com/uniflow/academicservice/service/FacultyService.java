package com.uniflow.academicservice.service;

import com.uniflow.academicservice.dto.FacultyCreateDto;
import com.uniflow.academicservice.dto.FacultyResponseDto;
import com.uniflow.academicservice.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyService {

    List<FacultyResponseDto> getAllFaculties();
    String createFaculty(FacultyCreateDto facultyCreateDto);
    FacultyResponseDto getFacultyByName(String name);
    void deleteFaculty(String name);
    FacultyResponseDto getFacultyById(long id);
    void validateFaculty(long id);
    String getFacultyNameById(long id);
    Long getFacultyIdByName(String name);
}

