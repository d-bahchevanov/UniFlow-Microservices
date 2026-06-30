package com.uniflow.enrollservice.client;

import com.uniflow.enrollservice.dto.client.StudentProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(
        name = "ACADEMIC",
        configuration = FeignConfig.class
)
public interface ProfileClient {
    @GetMapping("/api/profiles/me")
    StudentProfileResponseDto getStudentProfileInfo();
}
