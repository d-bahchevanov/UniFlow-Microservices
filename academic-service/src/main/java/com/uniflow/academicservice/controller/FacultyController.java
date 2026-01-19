package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.model.Faculty;
import com.uniflow.academicservice.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty-home")
@AllArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> createFaculty(@RequestParam String facultyName){
        return ResponseEntity.ok(facultyService.createFaculty(facultyName));
    }
}
