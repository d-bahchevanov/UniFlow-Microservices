package com.uniflow.enrollservice.service;

import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;

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
    List<SubjectInfoDto> getAvailableSubjectsToEnroll();
}
