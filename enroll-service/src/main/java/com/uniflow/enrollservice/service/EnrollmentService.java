package com.uniflow.enrollservice.service;

import com.uniflow.enrollservice.dto.EnrollmentStatusMessageDto;
import com.uniflow.enrollservice.dto.PointsMsgDto;
import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;

import java.util.List;



public interface EnrollmentService {
    EnrollmentResponseDto enrollStudent(EnrollmentRequestDto enrollmentRequestDto);

    EnrollmentStatusMessageDto approveEnrollment(Long enrollmentId);

    EnrollmentStatusMessageDto rejectEnrollment(Long enrollmentId);

    PointsMsgDto addPoints(
            Long enrollmentId,
            int points
    );
    EnrollmentStatusMessageDto finishEnrollment(Long enrollmentId);

    List<EnrollmentResponseDto> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentResponseDto> getEnrollmentsBySubject(String subjectName);
    List<SubjectInfoDto> getAvailableSubjectsToEnroll();
}
