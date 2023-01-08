package com.vmware.gemfire.spring.redis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GfRedisServer {

    /**
     * --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true
     */
    public static void main(String[] args) {

        System.setProperty("gemfire-for-redis-port","6379");
        System.setProperty("gemfire-for-redis-enabled","true");

        SpringApplication.run(GfRedisServer.class);
    }
}
