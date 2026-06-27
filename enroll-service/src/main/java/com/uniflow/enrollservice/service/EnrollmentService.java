package com.uniflow.enrollservice.service;

import com.uniflow.enrollservice.dto.DomainNameDto;
import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.List;



public interface EnrollmentService {
    EnrollmentResponseDto enrollStudent(EnrollmentRequestDto enrollmentRequestDto);

    void approveEnrollment(Long enrollmentId);

    void rejectEnrollment(Long enrollmentId);

    void assignPoints(
            Long enrollmentId,
            int points
    );
    void addPoints(
            Long enrollmentId,
            int points
    );
    void finishEnrollment(Long enrollmentId);

    List<EnrollmentResponseDto> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentResponseDto> getEnrollmentsBySubject(String subjectName);
    List<DomainNameDto> getAvailableSubjectsToEnroll();
}
