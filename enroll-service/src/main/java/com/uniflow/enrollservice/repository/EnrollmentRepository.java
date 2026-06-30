package com.uniflow.enrollservice.repository;

import com.uniflow.enrollservice.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndSubjectIdAndYear(
            Long studentId,
            long subjectId,
            int year
    );

    List<Enrollment> findAllByStudentId(Long studentId);

    List<Enrollment> findAllBySubjectId(long subjectId);
}
