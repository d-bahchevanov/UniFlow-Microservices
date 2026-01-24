package com.uniflow.academicservice.exception.domain.subject;

public class SubjectExistsException extends RuntimeException {
    public SubjectExistsException(String message) {
        super(message);
    }
}
