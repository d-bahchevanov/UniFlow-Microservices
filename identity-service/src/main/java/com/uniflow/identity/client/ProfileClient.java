package com.uniflow.identity.client;

import com.uniflow.identity.dto.CreateProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PROFILE")
public interface ProfileClient {

    @PostMapping("/api/profiles/create")
    void createProfile(CreateProfileRequest request);

}