package com.uniflow.enrollservice.client;

import com.uniflow.enrollservice.dto.client.SpecializationResponseDto;
import com.uniflow.enrollservice.dto.client.SubjectInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "ACADEMIC",
        contextId = "academicClientForEnrollment",
        configuration = FeignConfig.class
)
public interface AcademyClient {
    @GetMapping("/subject/available")
    List<SubjectInfoDto> getAvailableSubjects();
    @GetMapping("/specialization/internal/get/id/{id}")
    String getSpecializationNameByIdInternal(@PathVariable long id);
    @GetMapping("/faculty/internal/get/id/{id}")
    String getFacultyNameByIdInternal(@PathVariable long id);
    @GetMapping("/subject/internal/get/id/{id}")
    String getSubjectNameByIdInternal(@PathVariable long id);
    @GetMapping("/subject/internal/get/name/{name}")
    Long getSubjectIdByNameInternal(@PathVariable String name);
}
