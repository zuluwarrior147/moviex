package com.rental.moviex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class MoviexConfiguration {

    @Bean
    public Clock systemClock() {
        return Clock.systemDefaultZone();
    }
}
