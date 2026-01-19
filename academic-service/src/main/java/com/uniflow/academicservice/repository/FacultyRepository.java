package com.uniflow.academicservice.repository;

import com.uniflow.academicservice.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsFacultyByName(String name);
    Optional<Faculty> getFacultyByName(String name);
    List<Faculty> findAll();
}
