package com.uniflow.profileservice.dto.professor;

import com.uniflow.profileservice.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfessorResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
    private Title title;
}
