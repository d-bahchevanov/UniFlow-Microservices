package com.uniflow.identity.exception.handler;

import com.uniflow.identity.dto.ErrorResponseDto;
import com.uniflow.identity.exception.domain.ExistingEmailException;

import com.uniflow.identity.exception.domain.ExistingUsernameException;
import com.uniflow.identity.exception.domain.UserNotFoundException;
import com.uniflow.identity.exception.domain.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            ExistingEmailException.class,
            ExistingUsernameException.class,
            WrongPasswordException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequest(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
}
