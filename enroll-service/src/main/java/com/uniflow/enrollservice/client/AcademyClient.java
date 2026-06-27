package com.uniflow.enrollservice.client;

import com.uniflow.enrollservice.dto.DomainNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "ACADEMIC",
        configuration = FeignConfig.class
)
public interface AcademyClient {
    @GetMapping("/subject/available")
    List<DomainNameDto> getAvailableSubjects();
}
