package com.uniflow.profileservice.dto.profile.request;

import com.uniflow.profileservice.enums.Role;
import jakarta.validation.constraints.NotNull;

public record CreateProfileRequest(
        @NotNull
        Long userId,
        @NotNull
        String username,
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        Role role
) {
}
