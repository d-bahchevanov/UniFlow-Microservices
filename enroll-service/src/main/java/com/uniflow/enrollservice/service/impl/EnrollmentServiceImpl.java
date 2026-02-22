package com.uniflow.enrollservice.service.impl;

import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.enums.EnrollmentStatus;
import com.uniflow.enrollservice.exception.domain.EnrollmentAlreadyExistsException;
import com.uniflow.enrollservice.exception.domain.EnrollmentNotFoundException;
import com.uniflow.enrollservice.exception.domain.InvalidEnrollmentStateException;
import com.uniflow.enrollservice.exception.domain.InvalidPointsException;
import com.uniflow.enrollservice.model.Enrollment;
import com.uniflow.enrollservice.repository.EnrollmentRepository;
import com.uniflow.enrollservice.service.EnrollmentService;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.uniflow.enrollservice.enums.EnrollmentStatus.APPROVED;
import static com.uniflow.enrollservice.enums.EnrollmentStatus.PENDING;

@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentResponseDto enrollStudent(Long studentId, String facultyName, String specializationName, String subjectName, int semester) {
        if (enrollmentRepository.findByStudentIdAndSubjectNameAndSemester(studentId, subjectName, semester).isPresent()) {
            throw new EnrollmentAlreadyExistsException("This enrollment for this student already exists");
        }
        Enrollment enrollment = new Enrollment(studentId, facultyName, specializationName, subjectName, semester, PENDING);
        enrollmentRepository.save(enrollment);
        return new EnrollmentResponseDto(studentId, facultyName, specializationName, subjectName, semester);
    }

    @Override
    public void approveEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not pending");
        }
        enrollment.setStatus(EnrollmentStatus.APPROVED);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void rejectEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != PENDING) {
            throw new InvalidEnrollmentStateException("Enrollment is not pending");
        }
        enrollment.setStatus(EnrollmentStatus.REJECTED);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void assignPoints(Long enrollmentId, int points) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new EnrollmentNotFoundException("No such enrollment"));
        if (enrollment.getStatus() != APPROVED) {
            throw new InvalidEnrollmentStateException("Enrollment is not approved");
        }
        int pointsToAdd = enrollment.getPoints() + points;
        if (pointsToAdd > 100) {
            throw new InvalidPointsException("Can not add than 100 points");
        }
        enrollment.setPoints(pointsToAdd);
        if (pointsToAdd >= 50) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        } else {
            enrollment.setStatus(EnrollmentStatus.FAILED);
        }
        enrollmentRepository.save(enrollment);
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findAllByStudentId(studentId).stream().map(e -> new EnrollmentResponseDto(
                e.getStudentId(),
                e.getFacultyName(),
                e.getSpecializationName(),
                e.getSubjectName(),
                e.getPoints()
                ))
                .toList();
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsBySubject(String subjectName) {
        return enrollmentRepository.findAllBySubjectName(subjectName).stream().map(e -> new EnrollmentResponseDto(
                        e.getStudentId(),
                        e.getFacultyName(),
                        e.getSpecializationName(),
                        e.getSubjectName(),
                        e.getPoints()
                ))
                .toList();
    }
}
