package com.uniflow.academicservice.repository;

import com.uniflow.academicservice.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    boolean existsSpecializationByName(String name);
    Optional<Specialization> findByName(String name);
    List<Specialization> findSpecializationsByFaculty_Name(String facultyName);
}
