package com.uniflow.enrollservice.controller;

import com.uniflow.enrollservice.dto.EnrollmentStatusMessageDto;
import com.uniflow.enrollservice.dto.PointsMsgDto;
import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollment/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(enrollmentRequestDto));
    }
    @GetMapping("/enrollment/student/enrollments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId));
    }
    @GetMapping("/enrollment/subject/enrollments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsBySubject(@RequestParam String subjectName) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsBySubject(subjectName));
    }
    @PutMapping("/enrollment/approve")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<EnrollmentStatusMessageDto> approve(@RequestParam Long enrollmentId) {
        return ResponseEntity.ok(enrollmentService.approveEnrollment(enrollmentId));
    }
    @PutMapping("/enrollment/reject")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<EnrollmentStatusMessageDto> reject(@RequestParam Long enrollmentId) {
        return ResponseEntity.ok(enrollmentService.rejectEnrollment(enrollmentId));
    }
    @PutMapping("/enrollment/add/points")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<PointsMsgDto> addPoints(@RequestParam Long enrollmentId, @RequestParam int points) {
        return ResponseEntity.ok(enrollmentService.addPoints(enrollmentId, points));
    }
    @PutMapping("/enrollment/finish")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<EnrollmentStatusMessageDto> finish(@RequestParam Long enrollmentId) {
        return ResponseEntity.ok(enrollmentService.finishEnrollment(enrollmentId));
    }
    @GetMapping("/subjects/available")
    public ResponseEntity<List<SubjectInfoDto>> getAvailableSubjects() {
        return ResponseEntity.ok(enrollmentService.getAvailableSubjectsToEnroll());
    }
}
