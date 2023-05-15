package com.example.napasecomgw;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class NapasEcomGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(NapasEcomGwApplication.class, args);
    }

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Số lượng luồng tối thiểu
        executor.setMaxPoolSize(20); // Số lượng luồng tối đa
        executor.setQueueCapacity(100); // Số lượng công việc được chờ đợi
        return executor;
    }
}
