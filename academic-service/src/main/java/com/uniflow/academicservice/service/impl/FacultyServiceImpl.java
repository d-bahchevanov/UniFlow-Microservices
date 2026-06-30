package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.dto.DomainNameDto;
import com.uniflow.academicservice.dto.FacultyResponseDto;
import com.uniflow.academicservice.dto.SpecializationResponseDto;
import com.uniflow.academicservice.dto.SubjectResponseDto;
import com.uniflow.academicservice.exception.domain.faculty.FacultyExistsException;
import com.uniflow.academicservice.exception.domain.faculty.FacultyNotFoundException;
import com.uniflow.academicservice.model.Faculty;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.repository.FacultyRepository;
import com.uniflow.academicservice.repository.SpecializationRepository;
import com.uniflow.academicservice.service.FacultyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final SpecializationRepository specializationRepository;
    @Override
    public List<FacultyResponseDto> getAllFaculties() {
       return facultyRepository.findAll().stream().map(f -> {
                List<Specialization> specializations = specializationRepository.findSpecializationsByFaculty_Name(f.getName());
                List<DomainNameDto> specializationResponseDtoList = specializations.stream().map(s -> new DomainNameDto(s.getName())).toList();
                return new FacultyResponseDto(
                        f.getName(),
                        specializationResponseDtoList
                );
               })
               .toList();
    }

    @Override
    public String createFaculty(String name) {
        if (facultyRepository.existsFacultyByName(name)) {
            throw new FacultyExistsException("This faculty already exists");
        }
        Faculty faculty = new Faculty(name);
        facultyRepository.save(faculty);
        return faculty.getName();
    }

    @Override
    public FacultyResponseDto getFacultyByName(String name) {
        Faculty faculty = facultyRepository.getFacultyByName(name).orElseThrow(() -> new FacultyNotFoundException("No such faculty exists"));
        List<Specialization> specialization = specializationRepository.findSpecializationsByFaculty_Name(faculty.getName());
        List<DomainNameDto> list = specialization.stream().map(s -> new DomainNameDto(s.getName())).toList();
        return new FacultyResponseDto(faculty.getName(), list);
    }

    @Override
    @Transactional
    public void deleteFaculty(String name) {
        if (!facultyRepository.existsFacultyByName(name)) {
            throw new FacultyNotFoundException("No such faculty exists");
        }
        facultyRepository.deleteFacultyByName(name);
    }

    @Override
    public FacultyResponseDto getFacultyById(long id) {
        Faculty faculty = facultyRepository.getFacultyById(id).orElseThrow(() -> new FacultyNotFoundException("No such faculty exists"));
        List<DomainNameDto> specializationList = specializationRepository.findSpecializationsByFaculty_Name(faculty.getName())
                .stream()
                .map(s -> new DomainNameDto(s.getName()))
                .toList();
        return new FacultyResponseDto(faculty.getName(), specializationList);
    }
    @Override
    public void validateFaculty(long id) {
        facultyRepository.findById(id)
                .orElseThrow(() ->
                        new FacultyNotFoundException("Faculty with this id does not exist"));
    }

    @Override
    public String getFacultyNameById(long id) {
        FacultyResponseDto facultyResponseDto = getFacultyById(id);
        return facultyResponseDto.getName();
    }
}
