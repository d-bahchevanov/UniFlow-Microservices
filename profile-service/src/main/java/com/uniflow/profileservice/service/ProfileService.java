package com.uniflow.profileservice.service;


import com.uniflow.profileservice.dto.profile.ProfileResponseDto;
import com.uniflow.profileservice.dto.profile.ProfileRequestDto;
import com.uniflow.profileservice.dto.professor.ProfessorResponseDto;
import com.uniflow.profileservice.dto.student.StudentResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ProfileService extends UserDetailsService {
    List<StudentResponseDto> viewStudentProfile(ProfileRequestDto studentRequestDto);
    List<ProfessorResponseDto> viewProfessorProfile(ProfileRequestDto professorRequestDto);
    ProfileResponseDto viewProfileByUsername(String username);
    ProfileResponseDto viewProfile();
}
