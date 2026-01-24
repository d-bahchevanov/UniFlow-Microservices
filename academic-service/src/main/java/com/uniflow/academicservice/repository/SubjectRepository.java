package com.uniflow.academicservice.repository;

import com.uniflow.academicservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsSubjectByName(String name);
    Optional<Subject> findByName(String name);
    List<Subject> findSubjectsBySpecialization_Name(String specializationName);
    boolean existsByNameAndSpecialization_Name(String name, String specializationName);
}
