package com.uniflow.profileservice.service.impl;
import com.uniflow.profileservice.client.AcademyClient;
import com.uniflow.profileservice.dto.profile.request.CreateProfileRequest;
import com.uniflow.profileservice.dto.profile.request.FacultySpecializationValidationRequest;
import com.uniflow.profileservice.dto.profile.response.AdminProfileResponseDto;
import com.uniflow.profileservice.dto.profile.response.StudentAcademicInfoDto;
import com.uniflow.profileservice.dto.profile.response.StudentProfileResponseDto;
import com.uniflow.profileservice.dto.update.request.AdminUpdateRequestDto;
import com.uniflow.profileservice.dto.update.response.AdminUpdateResponseDto;
import com.uniflow.profileservice.dto.update.response.UpdateOwnProfileResponseDto;
import com.uniflow.profileservice.dto.profile.intr.ProfileResponseDto;
import com.uniflow.profileservice.dto.profile.request.ProfileRequestDto;
import com.uniflow.profileservice.dto.profile.response.ProfessorProfileResponseDto;
import com.uniflow.profileservice.enums.Role;
import com.uniflow.profileservice.exception.domain.ProfileAlreadyExistException;
import com.uniflow.profileservice.exception.domain.ProfileNotFoundException;
import com.uniflow.profileservice.model.Profile;
import com.uniflow.profileservice.repository.ProfileRepository;
import com.uniflow.profileservice.security.jwt.JwtService;
import com.uniflow.profileservice.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.springframework.security.core.userdetails.User.builder;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final AcademyClient academyClient;
    @Override
    @Transactional
    public void createProfile(CreateProfileRequest request) {
        profileRepository.findByUserId(request.userId())
                .ifPresent(profile -> {
                    throw new ProfileAlreadyExistException("Profile already exists for user");
                });
        Profile profile = Profile.builder()
                .userId(request.userId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();

        profileRepository.save(profile);
    }

    @Override
    @Transactional
    public void deleteProfile(Long id) {
        profileRepository.deleteProfileByUserId(id);
    }

    @Override
    public StudentAcademicInfoDto getStudentAcademicInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Profile profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        return new StudentAcademicInfoDto(
                profile.getSpecializationId(),
                profile.getYearOfStudy()
        );
    }

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
        List<Profile> foundProfiles = profiles.stream().filter(p -> p.getRole() == Role.STUDENT).toList();
        if (foundProfiles.isEmpty()) {
            throw new ProfileNotFoundException("No such student");
        }
        return foundProfiles.stream()
                .map(p -> new StudentProfileResponseDto(
                        p.getUserId(),
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFacultyId(),
                        p.getSpecializationId(),
                        p.getYearOfStudy(),
                        p.getRole(),
                        p.getAcademicTitle()
                ))
                .toList();
    }

    @Override
    public List<ProfessorProfileResponseDto> viewProfessorProfile(ProfileRequestDto profileRequestDto) {
        List<Profile> profiles = profileRepository.findAllByFirstNameAndLastName(profileRequestDto.getFirstName(), profileRequestDto.getLastName());
        if (profiles.isEmpty()) {
            throw new ProfileNotFoundException("No profiles found");
        }
        List<Profile> foundProfiles = profiles.stream().filter(p -> p.getRole() == Role.PROFESSOR).toList();
        if (foundProfiles.isEmpty()) {
            throw new ProfileNotFoundException("No such professor");
        }
        return foundProfiles.stream()
                .map(p -> new ProfessorProfileResponseDto(
                        p.getUsername(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getFacultyId(),
                        p.getAcademicTitle(),
                        p.getRole()))
                .toList();
    }

    @Override
    public ProfileResponseDto viewProfileByUsername(String username) {
        Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new ProfileNotFoundException("No profile with this username"));
        if (profile.getRole() == Role.STUDENT) {
            return new StudentProfileResponseDto(profile.getUserId(), profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFacultyId(), profile.getSpecializationId(), profile.getYearOfStudy(), profile.getRole(), profile.getAcademicTitle());
        }
        return new ProfessorProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFacultyId(), profile.getAcademicTitle(), profile.getRole());
    }

    @Override
    public ProfileResponseDto viewProfile() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        Profile profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        if (profile.getRole() == Role.STUDENT) {
            return new StudentProfileResponseDto(profile.getUserId(), profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFacultyId(), profile.getSpecializationId(), profile.getYearOfStudy(), profile.getRole(), profile.getAcademicTitle());
        }
        else if (profile.getRole() == Role.PROFESSOR) {
            return new ProfessorProfileResponseDto(profile.getUsername(), profile.getFirstName(), profile.getLastName(), profile.getFacultyId(), profile.getAcademicTitle(), profile.getRole());
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

    if (profileRequestDto.getFirstName() != null) {
        profile.setFirstName(profileRequestDto.getFirstName());
    }
    if (profileRequestDto.getLastName() != null) {
        profile.setLastName(profileRequestDto.getLastName());
    }

    profileRepository.save(profile);

    return new UpdateOwnProfileResponseDto(
            profile.getFirstName(),
            profile.getLastName()
    );
}

@Override
public AdminUpdateResponseDto updateProfileByAdmin(String username, AdminUpdateRequestDto adminUpdateRequestDto) {
    Profile profile = profileRepository.findProfileByUsername(username).orElseThrow(() -> new ProfileNotFoundException("This profile does not exist"));
    Long effectiveFacultyId =
            adminUpdateRequestDto.getFacultyId() != null
                    ? adminUpdateRequestDto.getFacultyId()
                    : profile.getFacultyId();
    if (adminUpdateRequestDto.getFacultyId()!= null) {
        academyClient.validateFaculty(effectiveFacultyId);
        profile.setFacultyId(adminUpdateRequestDto.getFacultyId());
    }
    if (adminUpdateRequestDto.getSpecializationId() != null && profile.getRole() == Role.STUDENT) {
        FacultySpecializationValidationRequest validationRequest = new FacultySpecializationValidationRequest(effectiveFacultyId, adminUpdateRequestDto.getSpecializationId());
        academyClient.validateFacultySpecialization(validationRequest);
        profile.setSpecializationId(adminUpdateRequestDto.getSpecializationId());
    }

    if (adminUpdateRequestDto.getYearOfStudy() != null && profile.getRole() == Role.STUDENT) {
        profile.setYearOfStudy(adminUpdateRequestDto.getYearOfStudy());
    }

    if (adminUpdateRequestDto.getAcademicTitle() != null) {
        profile.setAcademicTitle(adminUpdateRequestDto.getAcademicTitle());
    }

    profileRepository.save(profile);
    return new AdminUpdateResponseDto(profile.getFacultyId(), profile.getSpecializationId(), profile.getYearOfStudy(), profile.getAcademicTitle());
    }
}
