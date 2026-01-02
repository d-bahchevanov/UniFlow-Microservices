package com.uniflow.profileservice.exception.domain;
public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
