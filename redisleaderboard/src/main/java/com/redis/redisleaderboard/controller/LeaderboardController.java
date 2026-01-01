package com.redis.redisleaderboard.controller;

import com.redis.redisleaderboard.service.LeaderboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/{subject}")
    public List<Map<String, Object>> top10(@PathVariable String subject) {

        return leaderboardService.getTopN(subject, 2)
                .stream()
                .map(t -> {
                    String[] parts = t.getValue().split("\\|");
                    Map<String, Object> map = new HashMap<>();
                    map.put("studentId", parts[0]);
                    map.put("studentName", parts[1]);
                    map.put("score", t.getScore());
                    return map;
                })
                .toList();
    }
}
