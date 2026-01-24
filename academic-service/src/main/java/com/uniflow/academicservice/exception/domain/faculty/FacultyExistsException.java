package com.uniflow.academicservice.exception.domain.faculty;

public class FacultyExistsException extends RuntimeException {
    public FacultyExistsException(String message) {
        super(message);
    }
}
