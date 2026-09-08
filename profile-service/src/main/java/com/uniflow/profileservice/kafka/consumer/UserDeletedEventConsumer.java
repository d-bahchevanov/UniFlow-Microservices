package com.uniflow.profileservice.kafka.consumer;

import com.uniflow.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeletedEventConsumer {
    private final ProfileService profileService;
    @KafkaListener(topics = "user-deleted", groupId = "profile-group")
    public void consume(Long studentId) {
        profileService.deleteProfile(studentId);
    }
}
