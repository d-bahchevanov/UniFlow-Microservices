package com.uniflow.profileservice.kafka.consumer;

import com.uniflow.profileservice.kafka.event.UserRegisteredEvent;
import com.uniflow.profileservice.model.Profile;
import com.uniflow.profileservice.repository.ProfileRepository;
import com.uniflow.profileservice.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventConsumer {
    private final ProfileService profileService;
    @KafkaListener(topics = "user-created", groupId = "profile-group")
    public void consume(UserRegisteredEvent event) {
        profileService.createProfile(event);
    }
}
