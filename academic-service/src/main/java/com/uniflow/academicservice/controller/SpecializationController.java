package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.model.Faculty;
import com.uniflow.academicservice.model.Specialization;
import com.uniflow.academicservice.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialization-home")
@AllArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> createFaculty(@RequestParam String specializationName){
        return ResponseEntity.ok(specializationService.createSpecialization(specializationName));
    }
}
