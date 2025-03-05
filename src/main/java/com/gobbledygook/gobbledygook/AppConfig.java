package com.gobbledygook.gobbledygook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /* Spring uses this to inject a global GameSession object into any classes that depend on it */
    @Bean
    public GameSession getSession() {
        return new GameSession();
    }
}