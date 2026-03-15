package com.uniflow.identity.controller;

import com.uniflow.identity.dto.LoginRequestUserDto;
import com.uniflow.identity.dto.LoginResponseDto;
import com.uniflow.identity.dto.ResponseUserDto;
import com.uniflow.identity.dto.CreateRequestUserDto;
import com.uniflow.identity.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseUserDto> createUser(@Valid @RequestBody CreateRequestUserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/list")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<ResponseUserDto>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestUserDto userDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.login(userDto));
    }
}
