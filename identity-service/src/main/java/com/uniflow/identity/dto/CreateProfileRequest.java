package com.uniflow.identity.dto;

import com.uniflow.identity.enums.Role;

public record CreateProfileRequest(
        Long userId,
        String username,
        String email,
        String password,
        Role role
) {}
