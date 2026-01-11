package com.uniflow.profileservice.service.impl;

import com.uniflow.profileservice.dto.profile.response.AdminProfileResponseDto;
import com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto;
import com.uniflow.profileservice.dto.update.request.AdminUpdateRequestDto;
import com.uniflow.profileservice.dto.update.response.AdminUpdateResponseDto;
import com.uniflow.profileservice.dto.update.response.UpdateOwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.intr.OwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.request.ProfileRequestDto;
import com.uniflow.profileservice.dto.profile.response.ProfessorProfileResponseDto;
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
    public List<StudentProfileResponseDto> viewStudentProfile(ProfileRequestDto profileRequestDto) {
        List<Profile> profiles = profileRepository.findAllByFirstNameAndLastName(profileRequestDto.getFirstName(), profileRequestDto.getLastName());
        if (profiles.isEmpty()) {
            throw new ProfileNotFoundException("No profiles found");
        }
        return profiles.stream()
                .filter(p -> p.getRole() == Role.STUDENT)
                .map(p -> new StudentProfileResponseDto(
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFaculty(),
                        p.getSpecialization(),
                        p.getYearOfStudy(),
                        p.getRole()))
                .toList();
    }

    @Override
    public List<ProfessorProfileResponseDto> viewProfessorProfile(ProfileRequestDto profileRequestDto) {
        List<Profile> profiles = profileRepository.findAllByFirstNameAndLastName(profileRequestDto.getFirstName(), profileRequestDto.getLastName());
        if (profiles.isEmpty()) {
            throw new ProfileNotFoundException("No profiles found");
        }
        return profiles.stream()
                .filter(p -> p.getRole() == Role.PROFESSOR)
                .map(p -> new ProfessorProfileResponseDto(
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFaculty(),
                        p.getTitle(),
                        p.getRole()))
                .toList();
    }

    @Override
    public com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto viewProfileByUsername(String username) {
        Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new ProfileNotFoundException("No profile with this username"));
        return new com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFaculty(), profile.getSpecialization(), profile.getYearOfStudy(), profile.getRole());
    }

    @Override
    public OwnProfileResponseDto viewProfile() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        Profile profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        if (profile.getRole() == Role.STUDENT) {
            return new com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFaculty(), profile.getSpecialization(), profile.getYearOfStudy(), profile.getRole());
        }
        else if (profile.getRole() == Role.PROFESSOR) {
            return new ProfessorProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFaculty(), profile.getTitle(), profile.getRole());
        }
        return new AdminProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getRole());
}

@Override
public UpdateOwnProfileResponseDto updateOwnProfile(ProfileRequestDto profileRequestDto) {
    String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                    .getAuthentication())
            .getName();

    Profile profile = profileRepository.findProfileByUsername(username)
            .orElseThrow(() -> new ProfileNotFoundException("This profile does not exist"));

    profile.setFirstName(profileRequestDto.getFirstName());
    profile.setLastName(profileRequestDto.getLastName());

    profileRepository.save(profile);

    return new UpdateOwnProfileResponseDto(
            profile.getFirstName(),
            profile.getLastName()
    );
}

@Override
public AdminUpdateResponseDto updateProfileByAdmin(String username, AdminUpdateRequestDto adminUpdateRequestDto) {
    Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new ProfileNotFoundException("This profile does not exist"));
    if (adminUpdateRequestDto.getFaculty() != null) {
        profile.setFaculty(adminUpdateRequestDto.getFaculty());
    }

    if (adminUpdateRequestDto.getSpecialization() != null && profile.getRole() == Role.STUDENT) {
        profile.setSpecialization(adminUpdateRequestDto.getSpecialization());
    }

    if (adminUpdateRequestDto.getYearOfStudy() != null && profile.getRole() == Role.STUDENT) {
        profile.setYearOfStudy(adminUpdateRequestDto.getYearOfStudy());
    }

    if (adminUpdateRequestDto.getTitle() != null && profile.getRole() == Role.PROFESSOR) {
        profile.setTitle(adminUpdateRequestDto.getTitle());
    }

    profileRepository.save(profile);
    return new AdminUpdateResponseDto(profile.getFaculty(), profile.getSpecialization(), profile.getYearOfStudy(), profile.getTitle());
    }
}
