package com.redis.redisleaderboard.producer;

import com.redis.redisleaderboard.dto.ScoreEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScoreProducer {

    private final KafkaTemplate<String, ScoreEvent> kafkaTemplate;
    private static final String TOPIC = "student-score-topic";

    public ScoreProducer(KafkaTemplate<String, ScoreEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendScore(ScoreEvent event) {
        kafkaTemplate.send(TOPIC, event.getSubject(), event);
    }
}
