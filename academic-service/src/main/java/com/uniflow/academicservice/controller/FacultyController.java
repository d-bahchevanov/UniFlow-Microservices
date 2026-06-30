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
    @GetMapping("/admin/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyResponseDto> getFacultyById(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<String> getFacultyNameByIdInternal(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getFacultyNameById(id));
    }
    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFaculty(@PathVariable String name) {
        facultyService.deleteFaculty(name);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/exists/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> validateFaculty(@PathVariable long id) {
        facultyService.validateFaculty(id);
        return ResponseEntity.ok().build();
    }
}
