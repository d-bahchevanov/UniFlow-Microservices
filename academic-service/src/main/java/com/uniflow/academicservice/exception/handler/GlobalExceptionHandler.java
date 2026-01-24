package com.uniflow.academicservice.exception.handler;

import com.uniflow.academicservice.dto.ErrorResponseDto;
import com.uniflow.academicservice.exception.domain.faculty.FacultyExistsException;
import com.uniflow.academicservice.exception.domain.faculty.FacultyNotFoundException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationExistsException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationFacultyMismatchException;
import com.uniflow.academicservice.exception.domain.specialization.SpecializationNotFoundException;
import com.uniflow.academicservice.exception.domain.subject.SubjectExistsException;
import com.uniflow.academicservice.exception.domain.subject.SubjectNotFoundException;
import com.uniflow.academicservice.exception.domain.subject.SubjectSpecializationMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            FacultyNotFoundException.class,
            SpecializationNotFoundException.class,
            SubjectNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler({
            FacultyExistsException.class,
            SpecializationExistsException.class,
            SubjectExistsException.class,
            SpecializationFacultyMismatchException.class,
            SubjectSpecializationMismatchException.class
    })
    public ResponseEntity<ErrorResponseDto> handleExistsException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(
                e.getMessage(), LocalDateTime.now().toString()));
    }
}
