package com.redis.rediscrud.config;

import com.redis.rediscrud.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 1. Create a validator for security (Required in 2025)
        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class) // Or "com.redis.rediscrud" for better security
                .build(); // worked for universal

        // 2. Build the serializer using the modern 2025 API
        GenericJacksonJsonRedisSerializer jsonSerializer = GenericJacksonJsonRedisSerializer.builder()
                .typeValidator(validator)
                .build();

        JacksonJsonRedisSerializer<User> userSerializer = new JacksonJsonRedisSerializer<>(User.class);
//        template.setValueSerializer(userSerializer)

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(userSerializer); // Now saves with @class info
        template.setHashValueSerializer(userSerializer);
//        template.setValueSerializer(jsonSerializer); //  work with each object but before do anything flush redis
//        template.setHashValueSerializer(jsonSerializer);

        return template;
    }
}
