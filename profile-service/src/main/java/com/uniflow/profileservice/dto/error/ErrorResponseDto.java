package com.uniflow.profileservice.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    private String message;
    private String timestamp;
}
