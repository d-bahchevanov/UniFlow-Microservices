package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.dto.SubjectResponseDto;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationFacultyMismatchException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationNotFoundException;
import com.uniflow.academicservice.exception.domain.subject.SubjectExistsException;
import com.uniflow.academicservice.exception.domain.subject.SubjectNotFoundException;
import com.uniflow.academicservice.exception.domain.subject.SubjectSpecializationMismatchException;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.model.Subject;
import com.uniflow.academicservice.repository.SpecializationRepository;
import com.uniflow.academicservice.repository.SubjectRepository;
import com.uniflow.academicservice.service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public List<SubjectResponseDto> getSubjectsBySpecialization(String specializationName) {
        return subjectRepository.findSubjectsBySpecialization_Name(specializationName).stream()
                .map(s -> new SubjectResponseDto(s.getName(), s.getSpecialization().getName()))
                .toList();
    }

    @Override
    public SubjectResponseDto getSubjectByName(String name) {
        Subject subject = subjectRepository.findByName(name).orElseThrow(() -> new SubjectNotFoundException("No such subject"));
        return new SubjectResponseDto(subject.getName(), subject.getSpecialization().getName());
    }

    @Override
    public SubjectResponseDto createSubject(String subjectName, String specializationName) {
        if (subjectExistsByName(subjectName)) {
            throw new SubjectExistsException("Subject already exists");
        }
        Specialization specialization = specializationRepository.findByName(specializationName).orElseThrow(() -> new SpecializationNotFoundException("No such specialization"));
        Subject subject = new Subject(subjectName, specialization);
        subjectRepository.save(subject);
        return new SubjectResponseDto(subjectName, specializationName);
    }

    @Override
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(s -> new SubjectResponseDto(s.getName(), s.getSpecialization().getName()))
                .toList();
    }

    @Override
    public void validateSubjectBelongsToSpecialization(String subjectName, String specialization) {
        Subject subject = subjectRepository
                .findByName(subjectName)
                .orElseThrow(() ->
                        new SubjectNotFoundException("Subject does not exist")
                );
        if (!subject.getSpecialization().getName().equals(specialization)) {
            throw new SubjectSpecializationMismatchException(
                    "Subject does not belong to the given specialization"
            );
        }
    }

    @Override
    public boolean subjectExistsByName(String name) {
        return subjectRepository.existsSubjectByName(name);
    }

    @Override
    @Transactional
    public void deleteSubject(String name) {
        if (!subjectRepository.existsSubjectByName(name)) {
            throw new SubjectNotFoundException("No such subject exists");
        }
        subjectRepository.deleteSubjectByName(name);
    }
}
