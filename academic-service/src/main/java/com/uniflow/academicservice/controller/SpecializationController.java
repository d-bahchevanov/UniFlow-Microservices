package com.uniflow.academicservice.controller;

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
}
