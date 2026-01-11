package com.uniflow.profileservice.controller;

import com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto;
import com.uniflow.profileservice.dto.update.request.AdminUpdateRequestDto;
import com.uniflow.profileservice.dto.update.response.AdminUpdateResponseDto;
import com.uniflow.profileservice.dto.profile.response.ProfessorProfileResponseDto;
import com.uniflow.profileservice.dto.update.response.UpdateOwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.intr.OwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.request.ProfileRequestDto;
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
    public ResponseEntity<OwnProfileResponseDto> viewProfile(){
        return ResponseEntity.ok(profileService.viewProfile());
    }
    @GetMapping("/profile/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto> viewProfileByUsername(@PathVariable String username) {
        return ResponseEntity.ok(profileService.viewProfileByUsername(username));
    }
    @GetMapping("/search/student")
    public ResponseEntity<List<StudentProfileResponseDto>> viewStudent(@RequestBody @Valid ProfileRequestDto profileRequestDto) {
        return ResponseEntity.ok(profileService.viewStudentProfile(profileRequestDto));
    }
    @GetMapping("/search/professor")
    public ResponseEntity<List<ProfessorProfileResponseDto>> viewProfessor(@RequestBody @Valid ProfileRequestDto profileRequestDto) {
        return ResponseEntity.ok(profileService.viewProfessorProfile(profileRequestDto));
    }
    @PutMapping("/me/update")
    public ResponseEntity<UpdateOwnProfileResponseDto> updateProfileByUser(@RequestBody @Valid ProfileRequestDto profileRequestDto) {
        return ResponseEntity.ok(profileService.updateOwnProfile(profileRequestDto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/profile/update/{username}")
    public ResponseEntity<AdminUpdateResponseDto> updateProfileByAdmin(@PathVariable String username,  @RequestBody @Valid AdminUpdateRequestDto adminRequestDto) {
        return ResponseEntity.ok(profileService.updateProfileByAdmin(username, adminRequestDto));
    }
}

