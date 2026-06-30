package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.dto.FacultySpecializationValidationRequest;
import com.uniflow.academicservice.dto.SpecializationResponseDto;
import com.uniflow.academicservice.dto.DomainNameDto;
import com.uniflow.academicservice.exception.domain.faculty.FacultyNotFoundException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationExistsException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationFacultyMismatchException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationNotFoundException;
import com.uniflow.academicservice.model.Faculty;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.model.Subject;
import com.uniflow.academicservice.repository.FacultyRepository;
import com.uniflow.academicservice.repository.SpecializationRepository;
import com.uniflow.academicservice.repository.SubjectRepository;
import com.uniflow.academicservice.service.SpecializationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final FacultyRepository facultyRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public SpecializationResponseDto createSpecialization(String specializationName, String facultyName) {
        if (specializationRepository.existsSpecializationByName(specializationName)) {
            throw new SpecializationExistsException("Specialization already exists");
        }
        Faculty faculty = facultyRepository.getFacultyByName(facultyName).orElseThrow(() -> new FacultyNotFoundException("No such faculty"));
        Specialization specialization = new Specialization(specializationName, faculty);
        specializationRepository.save(specialization);
        return new SpecializationResponseDto(specializationName, facultyName, new ArrayList<>());
    }

    @Override
    public List<SpecializationResponseDto> getAllSpecializations() {
        return specializationRepository.findAll()
                .stream().map(spec -> {
                    List<DomainNameDto> subjects = subjectRepository
                            .findSubjectsBySpecialization_Name(spec.getName())
                            .stream()
                            .map(s -> new DomainNameDto(s.getName()))
                            .toList();

                    return new SpecializationResponseDto(
                            spec.getName(),
                            spec.getFaculty().getName(),
                            subjects
                    );
                }).toList();
    }

    @Override
    public List<SpecializationResponseDto> getSpecializationsByFaculty(String facultyName) {
        return specializationRepository
                .findSpecializationsByFaculty_Name(facultyName).stream().map(spec -> {

                    List<DomainNameDto> subjects = subjectRepository
                            .findSubjectsBySpecialization_Name(spec.getName())
                            .stream()
                            .map(s -> new DomainNameDto(s.getName())
                            )
                            .toList();

                    return new SpecializationResponseDto(
                            spec.getName(),
                            spec.getFaculty().getName(),
                            subjects
                    );
                }).toList();
    }

    @Override
    public SpecializationResponseDto getSpecializationByName(String name) {
        Specialization specialization = specializationRepository.findByName(name).orElseThrow(() -> new SpecializationNotFoundException("No such specialization"));
        List<DomainNameDto> subjects = subjectRepository.findSubjectsBySpecialization_Name(specialization.getName())
                .stream()
                .map(s -> new DomainNameDto(s.getName()))
                .toList();
        return new SpecializationResponseDto(specialization.getName(), specialization.getFaculty().getName(), subjects);
    }

    @Override
    public boolean specializationExistsByName(String name) {
        return specializationRepository.existsSpecializationByName(name);
    }

    @Override
    public void validateSpecializationBelongsToFaculty(String specializationName, String facultyName) {
        Specialization specialization = specializationRepository
                .findByName(specializationName)
                .orElseThrow(() ->
                        new SpecializationNotFoundException("Specialization does not exist")
                );
        if (!specialization.getFaculty().getName().equals(facultyName)) {
            throw new SpecializationFacultyMismatchException(
                    "Specialization does not belong to the given faculty"
            );
        }
    }

    @Override
    @Transactional
    public void deleteSpecialization(String name) {
        if (!specializationRepository.existsSpecializationByName(name)) {
            throw new SpecializationNotFoundException("No such specialization exists");
        }
        specializationRepository.deleteSpecializationByName(name);
    }

    @Override
    public SpecializationResponseDto getSpecializationById(long id) {
        if (!specializationRepository.existsById(id)) {
            throw new SpecializationNotFoundException("No such specialization exists");
        }
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() ->
                        new SpecializationNotFoundException("No specialization with such ID")
                );
        List<DomainNameDto> subjects = subjectRepository.findSubjectsBySpecialization_Name(specialization.getName())
                .stream()
                .map(s -> new DomainNameDto(s.getName()))
                .toList();
        return new SpecializationResponseDto(specialization.getName(), specialization.getFaculty().getName(), subjects);
    }

    @Override
    public void validateFacultySpecialization(FacultySpecializationValidationRequest request) {
        boolean exists = specializationRepository.existsSpecializationByIdAndFaculty_Id(request.getSpecializationId(), request.getFacultyId());
        if (!exists) {
            throw new SpecializationFacultyMismatchException("No specialization in this faculty");
        }
    }

    @Override
    public String getSpecializationNameById(long id) {
        SpecializationResponseDto specializationResponseDto = getSpecializationById(id);
        return specializationResponseDto.getName();
    }
}
