package com.uniflow.identity.client;

import com.uniflow.identity.dto.CreateProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "PROFILE",
        configuration = FeignConfig.class
)
public interface ProfileClient {

    @PostMapping("/api/profiles/create")
    void createProfile(CreateProfileRequest request);

    @DeleteMapping("/api/profiles/delete/{id}")
    void deleteProfile(@PathVariable Long id);
}