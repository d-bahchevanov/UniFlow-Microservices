package com.uniflow.enrollservice.controller;

import com.uniflow.enrollservice.dto.EnrollmentRequestDto;
import com.uniflow.enrollservice.dto.EnrollmentResponseDto;
import com.uniflow.enrollservice.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(enrollmentRequestDto.getStudentId(),
                enrollmentRequestDto.getFacultyName(),
                enrollmentRequestDto.getSpecializationName(),
                enrollmentRequestDto.getSubjectName(),
                enrollmentRequestDto.getSemester()));
    }
    @GetMapping("/student/enrollments")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId));
    }
    @GetMapping("/subject/enrollments")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsBySubject(@RequestParam String subjectName) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsBySubject(subjectName));
    }
    @PutMapping("/approve")
    public ResponseEntity<Void> approve(@RequestParam Long enrollmentId) {
        enrollmentService.approveEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/reject")
    public ResponseEntity<Void> reject(@RequestParam Long enrollmentId) {
        enrollmentService.rejectEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/assign")
    public ResponseEntity<Void> assignPoints(@RequestParam Long enrollmentId, @RequestParam int points) {
        enrollmentService.assignPoints(enrollmentId, points);
        return ResponseEntity.ok().build();
    }
}
