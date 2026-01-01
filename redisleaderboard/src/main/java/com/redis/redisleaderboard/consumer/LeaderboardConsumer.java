package com.redis.redisleaderboard.consumer;

import com.redis.redisleaderboard.dto.ScoreEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardConsumer {

    private final StringRedisTemplate redisTemplate;

    public LeaderboardConsumer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "student-score-topic", groupId = "leaderboard-group")
    public void consume(ScoreEvent event) {

        String redisKey = "leaderboard:" + event.getSubject();
        String member = event.getStudentId() + "|" + event.getStudentName();

        redisTemplate.opsForZSet()
                .add(redisKey, member, event.getScore());
    }
}
