package com.uniflow.academicservice.service;

import com.uniflow.academicservice.dto.SubjectResponseDto;

import java.util.List;

public interface SubjectService {
    List<SubjectResponseDto> getSubjectsBySpecialization(String specializationName);
    SubjectResponseDto getSubjectByName(String name);
    SubjectResponseDto createSubject(String subjectName, String specializationName);
    List<SubjectResponseDto> getAllSubjects();
    void validateSubjectBelongsToSpecialization(String subject, String specialization);
    boolean subjectExistsByName(String name);
    void deleteSubject(String name);
}

