package com.uniflow.enrollservice.client;

import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "ACADEMIC",
        contextId = "academicClient",
        configuration = FeignConfig.class
)
public interface AcademyClient {
    @GetMapping("/subject/available")
    List<SubjectInfoDto> getAvailableSubjects();
    @GetMapping("/specialization/get/{id}")
    String getSpecializationNameByIdInternal(@PathVariable long id);
    @GetMapping("/faculty/get/{id}")
    String getFacultyNameByIdInternal(@PathVariable long id);
    @GetMapping("/subject/get/{id}")
    SubjectInfoDto getSubjectInfoByIdInternal(@PathVariable long id);
    @GetMapping("/subject/get/{subjectName}")
    SubjectInfoDto getSubjectByName(@PathVariable String subjectName);
}
