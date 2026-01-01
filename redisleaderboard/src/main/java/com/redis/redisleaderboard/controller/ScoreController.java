package com.redis.redisleaderboard.controller;

import com.redis.redisleaderboard.dto.ScoreEvent;
import com.redis.redisleaderboard.producer.ScoreProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreProducer producer;

    public ScoreController(ScoreProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> publish(@RequestBody ScoreEvent event) {
        producer.sendScore(event);
        return ResponseEntity.ok("Record sent to Kafka");
    }
}
