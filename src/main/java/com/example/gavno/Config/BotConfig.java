package com.example.gavno.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("security.properties")
public class BotConfig {
    @Value("${bot.username}")
    String username;
    @Value("${bot.token}")
    String token;
}