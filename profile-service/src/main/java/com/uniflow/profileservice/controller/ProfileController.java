package com.uniflow.profileservice.controller;

import com.uniflow.profileservice.dto.profile.ProfileResponseDto;
import com.uniflow.profileservice.dto.profile.ProfileRequestDto;
import com.uniflow.profileservice.dto.professor.ProfessorResponseDto;
import com.uniflow.profileservice.dto.student.StudentResponseDto;
import com.uniflow.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDto> viewProfile(){
        return ResponseEntity.ok(profileService.viewProfile());
    }
    @GetMapping("/profile/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileResponseDto> viewProfileByUsername(@PathVariable String username) {
        return ResponseEntity.ok(profileService.viewProfileByUsername(username));
    }
    @GetMapping("/search/student")
    public ResponseEntity<List<StudentResponseDto>> viewStudent(@RequestBody @Valid ProfileRequestDto profileRequestDto) {
        return ResponseEntity.ok(profileService.viewStudentProfile(profileRequestDto));
    }
    @GetMapping("/search/professor")
    public ResponseEntity<List<ProfessorResponseDto>> viewProfessor(@RequestBody @Valid ProfileRequestDto profileRequestDto) {
        return ResponseEntity.ok(profileService.viewProfessorProfile(profileRequestDto));
    }
}

