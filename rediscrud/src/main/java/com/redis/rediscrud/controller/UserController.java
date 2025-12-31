package com.redis.rediscrud.controller;

import com.redis.rediscrud.model.User;
import com.redis.rediscrud.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserCacheService userCacheService;

    @PostMapping
    public String save(@RequestBody User user) {
        userCacheService.saveUser(user);
        return "User cached successfully";
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return userCacheService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userCacheService.deleteUser(id);
        return "User removed from cache";
    }
}
