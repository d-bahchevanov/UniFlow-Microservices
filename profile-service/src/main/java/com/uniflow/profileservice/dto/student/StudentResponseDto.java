package com.uniflow.profileservice.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
    private String specialization;
    private int yearOfStudy;
}
