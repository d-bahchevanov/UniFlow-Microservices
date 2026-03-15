package com.uniflow.profileservice.exception.handler;

import com.uniflow.profileservice.dto.error.ErrorResponseDto;
import com.uniflow.profileservice.exception.domain.AccessDeniedException;
import com.uniflow.profileservice.exception.domain.ProfileAlreadyExistException;
import com.uniflow.profileservice.exception.domain.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(ProfileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(ProfileAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleProfileAlreadyExistException(ProfileAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
}
