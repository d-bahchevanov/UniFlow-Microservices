package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.dto.FacultyResponseDto;
import com.uniflow.academicservice.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@AllArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createFaculty(@RequestParam String facultyName){
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFaculty(facultyName));
    }
    @GetMapping("/get")
    public ResponseEntity<List<FacultyResponseDto>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @GetMapping("/get/{name}")
    public ResponseEntity<FacultyResponseDto> getFacultyByName(@PathVariable String name) {
        return ResponseEntity.ok(facultyService.getFacultyByName(name));
    }
}
