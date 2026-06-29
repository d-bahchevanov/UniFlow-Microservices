package com.uniflow.academicservice.service;

import com.uniflow.academicservice.dto.DomainNameDto;
import com.uniflow.academicservice.dto.SubjectCreateDto;
import com.uniflow.academicservice.dto.SubjectResponseDto;
import com.uniflow.academicservice.dto.client.StudentAcademicInfoDto;
import com.uniflow.academicservice.dto.client.SubjectInfoDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SubjectService {
    List<SubjectResponseDto> getSubjectsBySpecialization(String specializationName);
    SubjectResponseDto getSubjectByName(String name);
    SubjectResponseDto createSubject(SubjectCreateDto subjectCreateDto);
    List<SubjectResponseDto> getAllSubjects();
    void validateSubjectBelongsToSpecialization(String subject, String specialization);
    boolean subjectExistsByName(String name);
    void deleteSubject(String name);
    List<SubjectInfoDto> getAvailableSubjectsToEnroll();
}

