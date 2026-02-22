package com.uniflow.enrollservice.repository;

import com.uniflow.enrollservice.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndSubjectNameAndSemester(
            Long studentId,
            String subjectName,
            int semester
    );

    List<Enrollment> findAllByStudentId(Long studentId);

    List<Enrollment> findAllBySubjectName(String subjectName);

    boolean existsByStudentId(Long studentId);
}
