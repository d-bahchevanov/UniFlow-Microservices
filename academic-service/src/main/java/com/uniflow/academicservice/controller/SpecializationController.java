package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.dto.FacultySpecializationValidationRequest;
import com.uniflow.academicservice.dto.SpecializationResponseDto;
import com.uniflow.academicservice.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialization")
@AllArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpecializationResponseDto> createSpecialization(@RequestParam String specializationName, @RequestParam String facultyName){
        return ResponseEntity.status(HttpStatus.CREATED).body(specializationService.createSpecialization(specializationName, facultyName));
    }
    @GetMapping("/get")
    public ResponseEntity<List<SpecializationResponseDto>> getAllSpecializations() {
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }
    @GetMapping("/admin/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpecializationResponseDto> getSpecializationById(@PathVariable long id) {
        return ResponseEntity.ok(specializationService.getSpecializationById(id));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<String> getSpecializationNameByIdInternal(@PathVariable long id) {
        return ResponseEntity.ok(specializationService.getSpecializationNameById(id));
    }
    @GetMapping("/get/{specializationName}")
    public ResponseEntity<SpecializationResponseDto> getSpecializationByName(@PathVariable String specializationName) {
        return ResponseEntity.ok(specializationService.getSpecializationByName(specializationName));
    }
    @GetMapping("/faculty/get/{facultyName}")
    public ResponseEntity<List<SpecializationResponseDto>> getSpecializationByFacultyName(@PathVariable String facultyName) {
        return ResponseEntity.ok(specializationService.getSpecializationsByFaculty(facultyName));
    }
    @PostMapping("/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> validate(@RequestParam String specializationName, @RequestParam String facultyName) {
        specializationService.validateSpecializationBelongsToFaculty(specializationName, facultyName);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable String name) {
        specializationService.deleteSpecialization(name);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/validate/faculty-specialization")
    public ResponseEntity<Void> validateFacultySpecialization(@RequestBody FacultySpecializationValidationRequest request) {
        specializationService.validateFacultySpecialization(request);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/internal/get/{name}")
    public ResponseEntity<Long> getSpecializationIdByNameInternal(@PathVariable String name) {
        return ResponseEntity.ok(specializationService.getSpecializationIdByName(name));
    }
}
