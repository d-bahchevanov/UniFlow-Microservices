package com.uniflow.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
            private String message;
            private String timestamp;
}
