package com.uniflow.profileservice.service.impl;

import com.uniflow.profileservice.dto.profile.ProfileResponseDto;
import com.uniflow.profileservice.dto.profile.ProfileRequestDto;
import com.uniflow.profileservice.dto.professor.ProfessorResponseDto;
import com.uniflow.profileservice.dto.student.StudentResponseDto;
import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.exception.domain.ProfileNotFoundException;
import com.uniflow.profileservice.model.Profile;
import com.uniflow.profileservice.repository.ProfileRepository;
import com.uniflow.profileservice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.security.core.userdetails.User.builder;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No profile with this username"));
        return builder()
                .username(profile.getUsername())
                .password("")
                .authorities("ROLE_" + profile.getRole().name())
                .build();
    }
    @Override
    public List<StudentResponseDto> viewStudentProfile(ProfileRequestDto profileRequestDto) {
        List<Profile> profiles = profileRepository.findAllByFirstNameAndLastName(profileRequestDto.getFirstName(), profileRequestDto.getLastName());
        if (profiles.isEmpty()) {
            throw new ProfileNotFoundException("No profiles found");
        }
        return profiles.stream()
                .filter(p -> p.getRole() == Role.STUDENT)
                .map(p -> new StudentResponseDto(
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFaculty(),
                        p.getSpecialization(),
                        p.getYearOfStudy()))
                .toList();
    }

    @Override
    public List<ProfessorResponseDto>viewProfessorProfile(ProfileRequestDto profileRequestDto) {
        List<Profile> profiles = profileRepository.findAllByFirstNameAndLastName(profileRequestDto.getFirstName(), profileRequestDto.getLastName());
        if (profiles.isEmpty()) {
            throw new ProfileNotFoundException("No profiles found");
        }
        return profiles.stream()
                .filter(p -> p.getRole() == Role.PROFESSOR)
                .map(p -> new ProfessorResponseDto(
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFaculty(),
                        p.getTile()))
                .toList();
    }

    @Override
    public ProfileResponseDto viewProfileByUsername(String username) {
        Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new ProfileNotFoundException("No profile with this username"));
        return new ProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFaculty());
    }

    @Override
    public ProfileResponseDto viewProfile() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        Profile profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        return new ProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFaculty());
    }
}
