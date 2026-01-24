package com.uniflow.academicservice.exception.domain.faculty;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(String message) {
        super(message);
    }
}
