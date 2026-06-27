package com.uniflow.academicservice.client;

import com.uniflow.academicservice.dto.client.StudentAcademicInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "PROFILE",
        configuration = FeignConfig.class
)
public interface ProfileClient {
    @GetMapping("/api/profiles/academic-info")
    StudentAcademicInfoDto getStudentAcademicInfo();
}
