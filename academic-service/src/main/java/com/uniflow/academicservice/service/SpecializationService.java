package com.uniflow.academicservice.service;
import com.uniflow.academicservice.dto.FacultySpecializationValidationRequest;
import com.uniflow.academicservice.dto.SpecializationResponseDto;
import java.util.List;
import java.util.Optional;

public interface SpecializationService {
    SpecializationResponseDto createSpecialization(String specializationName, String facultyName);
    List<SpecializationResponseDto> getAllSpecializations();
    List<SpecializationResponseDto> getSpecializationsByFaculty(String facultyName);
    SpecializationResponseDto getSpecializationByName(String name);
    boolean specializationExistsByName(String name);
    void validateSpecializationBelongsToFaculty(String specializationName, String facultyName);
    void deleteSpecialization(String name);
    SpecializationResponseDto getSpecializationById(long id);
    void validateFacultySpecialization(FacultySpecializationValidationRequest request);
    String getSpecializationNameById(long id);
    Long getSpecializationIdByName(String name);
}

