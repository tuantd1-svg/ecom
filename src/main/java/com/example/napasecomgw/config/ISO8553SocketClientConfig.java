package com.example.napasecomgw.config;

import com.example.napasecomgw.client.ISO8553SocketClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class ISO8553SocketClientConfig {

    @Bean
    public ISO8553SocketClient iso8553SocketClient() {
        return new ISO8553SocketClient();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }

    @Bean
    public ApplicationRunner applicationRunner(ISO8553SocketClient iso8553SocketClient) {
        return args -> {
            // Khởi động client Socket
            iso8553SocketClient.startClient();
        };
    }
}
