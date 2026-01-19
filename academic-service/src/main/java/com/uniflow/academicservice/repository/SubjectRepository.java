package com.uniflow.academicservice.repository;

import com.uniflow.academicservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsSubjectByName(String name);
}
