package com.uniflow.identity.kafka.producer;

import com.uniflow.identity.kafka.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredEventProducer {
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Publishing user registered event for userId: {}", event.userId());
        try {
            kafkaTemplate.send("user-created", event.userId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to send Kafka message for user: {}", event.userId(), e);
        }
    }
}
