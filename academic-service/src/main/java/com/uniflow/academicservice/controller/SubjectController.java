package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.dto.DomainNameDto;
import com.uniflow.academicservice.dto.SubjectCreateDto;
import com.uniflow.academicservice.dto.SubjectResponseDto;
import com.uniflow.academicservice.dto.client.StudentAcademicInfoDto;
import com.uniflow.academicservice.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDto> createSubject(@RequestBody SubjectCreateDto subjectCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.createSubject(subjectCreateDto));
    }
    @GetMapping("/get")
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @GetMapping("/specialization/get/{specializationName}")
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjectsBySpecialization(@PathVariable String specializationName) {
        return ResponseEntity.ok(subjectService.getSubjectsBySpecialization(specializationName));
    }
    @GetMapping("/get/{subjectName}")
    public ResponseEntity<SubjectResponseDto> getSubjectByName(@PathVariable String subjectName) {
        return ResponseEntity.ok(subjectService.getSubjectByName(subjectName));
    }
    @PostMapping("/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> validateSubjectBelongsToSpecialization(@RequestParam String subjectName, @RequestParam String specializationName) {
        subjectService.validateSubjectBelongsToSpecialization(subjectName, specializationName);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable String name) {
        subjectService.deleteSubject(name);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/available")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<DomainNameDto>> getAvailableSubjectsToEnroll() {
        return ResponseEntity.ok(subjectService.getAvailableSubjectsToEnroll());
    }
}
