package com.uniflow.academicservice.service.impl;
import com.uniflow.academicservice.model.Subject;
import com.uniflow.academicservice.repository.SubjectRepository;
import com.uniflow.academicservice.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    @Override
    public List<Subject> getBySpecialization(String specializationName) {
        return List.of();
    }

    @Override
    public Subject getSubjectByName(String name) {
        return null;
    }

    @Override
    public String createSubject(String name) {
        return null;
    }

    @Override
    public boolean validateSubjectBelongsToSpecialization(String subject, String specialization) {
        return false;
    }
}
