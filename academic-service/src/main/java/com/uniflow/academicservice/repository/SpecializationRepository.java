package com.uniflow.academicservice.repository;

import com.uniflow.academicservice.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    boolean existsSpecializationByName(String name);
}
