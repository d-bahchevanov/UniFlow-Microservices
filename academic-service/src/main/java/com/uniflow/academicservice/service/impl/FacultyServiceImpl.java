package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.dto.FacultyResponseDto;
import com.uniflow.academicservice.exception.domain.faculty.FacultyExistsException;
import com.uniflow.academicservice.exception.domain.faculty.FacultyNotFoundException;
import com.uniflow.academicservice.model.Faculty;
import com.uniflow.academicservice.repository.FacultyRepository;
import com.uniflow.academicservice.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    @Override
    public List<FacultyResponseDto> getAllFaculties() {
       return facultyRepository.findAll().stream().map(
                f -> new FacultyResponseDto(f.getName(), f.getSpecializations()))
               .toList();
    }

    @Override
    public FacultyResponseDto createFaculty(String name) {
        if (facultyRepository.existsFacultyByName(name)) {
            throw new FacultyExistsException("This faculty already exists");
        }
        Faculty faculty = new Faculty(name);
        facultyRepository.save(faculty);
        return new FacultyResponseDto(faculty.getName(), faculty.getSpecializations());
    }

    @Override
    public FacultyResponseDto getFacultyByName(String name) {
        Faculty faculty = facultyRepository.getFacultyByName(name).orElseThrow(() -> new FacultyNotFoundException("No such faculty exists"));
        return new FacultyResponseDto(faculty.getName(), faculty.getSpecializations());
    }
}
