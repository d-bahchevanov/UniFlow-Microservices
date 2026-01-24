package com.uniflow.academicservice.dto;
import com.uniflow.academicservice.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
@AllArgsConstructor
@Getter
public class SpecializationResponseDto {
    private String name;
    private String facultyName;
    private List<Subject> subjectsList;
}
