package com.uniflow.academicservice.service;

import com.uniflow.academicservice.enums.SpecializationEnum;
import com.uniflow.academicservice.enums.SubjectEnum;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getBySpecialization(String specializationName);
    Subject getSubjectByName(String name);
    String createSubject(String name);
    boolean validateSubjectBelongsToSpecialization(String subject, String specialization);
}

