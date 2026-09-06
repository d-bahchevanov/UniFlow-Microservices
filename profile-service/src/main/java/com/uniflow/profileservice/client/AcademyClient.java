package com.uniflow.profileservice.client;

import com.uniflow.profileservice.dto.profile.request.FacultySpecializationValidationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ACADEMIC",
        configuration = FeignConfig.class
)
public interface AcademyClient {
    @GetMapping("/faculty/exists/{id}")
    void validateFaculty(@PathVariable Long id);
    @PostMapping("/specialization/validate/faculty-specialization")
    void validateFacultySpecialization(@RequestBody FacultySpecializationValidationRequest request);
    @GetMapping("/specialization/internal/get/{name}")
    Long getSpecializationIdByNameInternal(@PathVariable String name);
    @GetMapping("/faculty/internal/get/{name}")
    Long getFacultyIdByNameInternal(@PathVariable String name);
}