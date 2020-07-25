package com.rental.moviex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Configuration
    public static class TestConfig {
        @Bean
        public Clock getSystemClock() {
            return Clock.systemDefaultZone();
        }
    }
}
