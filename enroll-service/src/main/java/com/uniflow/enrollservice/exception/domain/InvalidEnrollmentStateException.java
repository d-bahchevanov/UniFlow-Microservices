package com.uniflow.enrollservice.exception.domain;

public class InvalidEnrollmentStateException extends RuntimeException {
    public InvalidEnrollmentStateException(String message) {
        super(message);
    }
}
