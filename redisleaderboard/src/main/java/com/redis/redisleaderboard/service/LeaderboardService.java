package com.redis.redisleaderboard.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LeaderboardService {

    private final StringRedisTemplate redisTemplate;

    public LeaderboardService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<ZSetOperations.TypedTuple<String>> getTopN(
            String subject, int limit) {

        String key = "leaderboard:" + subject;

        return redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, 0, limit - 1);
    }
}
