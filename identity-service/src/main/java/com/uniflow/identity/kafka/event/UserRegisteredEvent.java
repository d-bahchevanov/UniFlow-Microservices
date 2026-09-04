package com.uniflow.identity.kafka.event;

import com.uniflow.identity.enums.Role;

public record UserRegisteredEvent(Long userId,
    String firstName,
    String lastName,
    String username,
    String email,
    Role role)
{}
