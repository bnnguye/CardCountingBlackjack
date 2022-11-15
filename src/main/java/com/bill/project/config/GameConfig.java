package com.bill.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cardcounter")
@Data
public class GameConfig {
    private int noOfDecks;
}
