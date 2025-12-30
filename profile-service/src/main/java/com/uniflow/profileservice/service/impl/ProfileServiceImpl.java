package com.uniflow.profileservice.service.impl;

import com.uniflow.profileservice.model.Profile;
import com.uniflow.profileservice.repository.ProfileRepository;
import com.uniflow.profileservice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
