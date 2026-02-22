package com.uniflow.enrollservice.service;

import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollmentService {
    EnrollmentResponseDto enrollStudent(
            Long studentId,
            String facultyName,
            String specializationName,
            String subjectName,
            int semester
    );

    void approveEnrollment(Long enrollmentId);

    void rejectEnrollment(Long enrollmentId);

    void assignPoints(
            Long enrollmentId,
            int points
    );

    List<EnrollmentResponseDto> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentResponseDto> getEnrollmentsBySubject(String subjectName);
}
