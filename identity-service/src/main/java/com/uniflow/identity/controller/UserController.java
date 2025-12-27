package com.uniflow.identity.controller;

import com.uniflow.identity.dto.LoginResponseDto;
import com.uniflow.identity.dto.ResponseUserDto;
import com.uniflow.identity.dto.RequestUserDto;
import com.uniflow.identity.exception.handler.GlobalExceptionHandler;
import com.uniflow.identity.model.User;
import com.uniflow.identity.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> registerUser(@Valid @RequestBody RequestUserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody RequestUserDto userDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.login(userDto));
    }
}
