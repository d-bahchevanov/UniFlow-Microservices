package com.uniflow.identity.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDeletedEventProducer {
    private final KafkaTemplate<String, Long> kafkaTemplate;
    public void sendUserDeletedEvent(Long userId) {
        log.info("Deleting user registered event for userId: {}", userId);
        try {
            kafkaTemplate.send("user-deleted", userId.toString(), userId);
        } catch (Exception e) {
            log.error("Failed to send Kafka message for user: {}", userId, e);
        }
    }
}
