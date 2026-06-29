package com.uniflow.enrollservice.service.impl;

import com.uniflow.enrollservice.client.AcademyClient;
import com.uniflow.enrollservice.client.ProfileClient;
import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.dto.client.StudentProfileResponseDto;
import com.uniflow.enrollservice.enums.EnrollmentStatus;
import com.uniflow.enrollservice.exception.domain.EnrollmentAlreadyExistsException;
import com.uniflow.enrollservice.exception.domain.EnrollmentNotFoundException;
import com.uniflow.enrollservice.exception.domain.InvalidEnrollmentStateException;
import com.uniflow.enrollservice.exception.domain.InvalidPointsException;
import com.uniflow.enrollservice.model.Enrollment;
import com.uniflow.enrollservice.repository.EnrollmentRepository;
import com.uniflow.enrollservice.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.uniflow.enrollservice.enums.EnrollmentStatus.*;

@AllArgsConstructor
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final AcademyClient academyClient;
    private final ProfileClient profileClient;
    @Override
    public EnrollmentResponseDto enrollStudent(EnrollmentRequestDto enrollmentRequestDto) {
        if (enrollmentRepository.existsByStudentIdAndSubjectNameAndYear(enrollmentRequestDto.getStudentId(), enrollmentRequestDto.getSubjectName(), enrollmentRequestDto.getYear())) {
            throw new EnrollmentAlreadyExistsException("This enrollment for this student already exists");
        }
        SubjectInfoDto subject = getAvailableSubjectsToEnroll().stream()
                .filter(s -> s.getName().equals(enrollmentRequestDto.getSubjectName()))
                .findFirst()
                .orElseThrow(() -> new InvalidEnrollmentStateException("Subject is not available for enrollment"));
            StudentProfileResponseDto studentProfileResponseDto = profileClient.getStudentProfileInfo();
            Enrollment enrollment = new Enrollment(studentProfileResponseDto.getStudentId(),
                    studentProfileResponseDto.getFacultyId(),
                    studentProfileResponseDto.getSpecializationId(),
                    subject.getId(),
                    studentProfileResponseDto.getYear(),
                    PENDING);
            enrollmentRepository.save(enrollment);
            return new EnrollmentResponseDto(studentProfileResponseDto.getStudentId(),
                    enrollmentRequestDto.getFacultyName(), //need new dto for response based on academy client
                    enrollmentRequestDto.getSpecializationName(), //need new dto for response based on academy client
                    subject.getName(),
                    studentProfileResponseDto.getYear(),
                    PENDING);
        }

    @Override
    @Transactional
    public void approveEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not pending");
        }
        enrollment.setStatus(EnrollmentStatus.APPROVED);
    }

    @Override
    @Transactional
    public void rejectEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not pending");
        }
        enrollment.setStatus(EnrollmentStatus.REJECTED);
    }

    @Override
    @Transactional
    public void assignPoints(Long enrollmentId, int points) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() == REJECTED || enrollment.getStatus() == PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not active");
        }
        if (points > 100) {
            throw new InvalidPointsException("Can not have more than 100 points");
        }
        enrollment.setPoints(points);
    }

    @Override
    @Transactional
    public void addPoints(Long enrollmentId, int points) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() == REJECTED || enrollment.getStatus() == PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not active");
        }
        int pointsAccumulated = enrollment.getPoints() + points;
        if (pointsAccumulated > 100) {
            throw new InvalidPointsException("Can not have more than 100 points");
        }
        enrollment.setPoints(pointsAccumulated);
    }

    @Override
    @Transactional
    public void finishEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != APPROVED) {
            throw new InvalidEnrollmentStateException("Enrollment is not active");
        }
        int points = enrollment.getPoints();
        if (points >= 50) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        } else {
            enrollment.setStatus(EnrollmentStatus.FAILED);
        }
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findAllByStudentId(studentId).stream().map(e -> new EnrollmentResponseDto(
                e.getStudentId(),
                e.getFacultyName(),
                e.getSpecializationName(),
                e.getSubjectName(),
                e.getPoints(),
                e.getStatus()
                )).filter(enrollmentResponseDto -> enrollmentResponseDto.getStatus() != PENDING &&
                        enrollmentResponseDto.getStatus() != REJECTED)
                .toList();
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsBySubject(String subjectName) {
        return enrollmentRepository.findAllBySubjectName(subjectName).stream().map(e -> new EnrollmentResponseDto(
                        e.getStudentId(),
                        e.getFacultyName(),
                        e.getSpecializationName(),
                        e.getSubjectName(),
                        e.getPoints(),
                        e.getStatus()
                ))
                .filter(enrollmentResponseDto -> enrollmentResponseDto.getStatus() != PENDING &&
                        enrollmentResponseDto.getStatus() != REJECTED)
                .toList();
    }

    @Override
    public List<SubjectInfoDto> getAvailableSubjectsToEnroll() {
        List<SubjectInfoDto> subjects = academyClient.getAvailableSubjects();
        return subjects.stream().toList();
    }
}
