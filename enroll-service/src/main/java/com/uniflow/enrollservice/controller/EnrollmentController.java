package com.uniflow.enrollservice.controller;

import com.uniflow.enrollservice.dto.admin.AdminEnrollmentResponseDto;
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
    public ResponseEntity<Void> approve(@RequestParam Long enrollmentId) {
        enrollmentService.approveEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/enrollment/reject")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> reject(@RequestParam Long enrollmentId) {
        enrollmentService.rejectEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/enrollment/assign")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> assignPoints(@RequestParam Long enrollmentId, @RequestParam int points) {
        enrollmentService.assignPoints(enrollmentId, points);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/enrollment/add/points")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> addPoints(@RequestParam Long enrollmentId, @RequestParam int points) {
        enrollmentService.addPoints(enrollmentId, points);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/enrollment/finish")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> finish(@RequestParam Long enrollmentId) {
        enrollmentService.finishEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/subjects/available")
    public ResponseEntity<List<SubjectInfoDto>> getAvailableSubjects() {
        return ResponseEntity.ok(enrollmentService.getAvailableSubjectsToEnroll());
    }
}
