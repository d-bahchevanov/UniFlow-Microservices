package com.uniflow.academicservice.service;

import com.uniflow.academicservice.enums.FacultyEnum;
import com.uniflow.academicservice.enums.SpecializationEnum;
import com.uniflow.academicservice.model.Specialization;

import java.util.List;

public interface SpecializationService {

    List<Specialization> getByFaculty(String facultyName);
    String createSpecialization(String name);
    Specialization getSpecializationByName(String name);
    boolean validateSpecializationBelongsToFaculty(String specialization, String faculty);
}

