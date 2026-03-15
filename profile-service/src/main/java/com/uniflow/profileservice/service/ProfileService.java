package com.uniflow.profileservice.service;


import com.uniflow.profileservice.dto.profile.request.CreateProfileRequest;
import com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto;
import com.uniflow.profileservice.dto.update.request.AdminUpdateRequestDto;
import com.uniflow.profileservice.dto.update.response.AdminUpdateResponseDto;
import com.uniflow.profileservice.dto.profile.response.ProfessorProfileResponseDto;
import com.uniflow.profileservice.dto.update.response.UpdateOwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.intr.ProfileResponseDto;
import com.uniflow.profileservice.dto.profile.request.ProfileRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ProfileService extends UserDetailsService {
    List<StudentProfileResponseDto> viewStudentProfile(ProfileRequestDto studentRequestDto);
    List<ProfessorProfileResponseDto> viewProfessorProfile(ProfileRequestDto professorRequestDto);
    ProfileResponseDto viewProfileByUsername(String username);
    ProfileResponseDto viewProfile();
    UpdateOwnProfileResponseDto updateOwnProfile(ProfileRequestDto profileRequestDto);
    AdminUpdateResponseDto updateProfileByAdmin(String username, AdminUpdateRequestDto adminUpdateRequestDto);
    void createProfile(CreateProfileRequest request);
}
