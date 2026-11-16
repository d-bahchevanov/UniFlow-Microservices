package com.uniflow.identity.config;

import com.uniflow.identity.dto.CreateRequestUserDto;
import com.uniflow.identity.enums.Role;
import com.uniflow.identity.repository.UserRepository;
import com.uniflow.identity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrapSeeder implements ApplicationRunner {

    private final UserRepository userRepository; //
    private final UserService userService; //

    @Value("${app.admin.username:}")
    private String adminUsername;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (adminUsername.isBlank() || adminPassword.isBlank()) {
            return;
        }

        if (!userRepository.existsByRole(Role.ADMIN)) {
            CreateRequestUserDto adminDto = new CreateRequestUserDto("System", "Admin", adminUsername, "admin@uniflow.com", adminPassword, 30, "+359000000000", Role.ADMIN);
            userService.createUser(adminDto);
        }
    }
}
