package com.uniflow.academicservice.controller;

import com.uniflow.academicservice.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject-home")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping
    public ResponseEntity<String> createSubject(@RequestParam String subjectName) {
        return ResponseEntity.ok(subjectService.createSubject(subjectName));
    }
}
