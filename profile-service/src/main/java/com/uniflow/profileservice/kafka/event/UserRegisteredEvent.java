package com.uniflow.profileservice.kafka.event;

import com.uniflow.profileservice.enums.Role;

public record UserRegisteredEvent(Long userId,
                                  String firstName,
                                  String lastName,
                                  String username,
                                  String email,
                                  Role role)
{}
