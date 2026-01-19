package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.repository.SpecializationRepository;
import com.uniflow.academicservice.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;
    @Override
    public List<Specialization> getByFaculty(String facultyName) {
        return List.of();
    }

    @Override
    public String createSpecialization(String name) {
        return null;
    }

    @Override
    public Specialization getSpecializationByName(String name) {
        return null;
    }

    @Override
    public boolean validateSpecializationBelongsToFaculty(String specialization, String faculty) {
        return false;
    }
}
