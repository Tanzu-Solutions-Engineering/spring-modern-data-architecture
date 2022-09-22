package com.vmware.springwebapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * RedisConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig
{
    @Value("${redis.server.name:localhost}")
    private String redisServerName;

    @Value("${redis.server.port:6379}")
    private int redisPort = 6379;


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisServerName,redisPort);
    }
}
