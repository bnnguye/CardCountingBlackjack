package com.bill.project.app;

import com.bill.project.config.GameConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GameConfig.class)
public class CardCountBlackjackApplication {

    public CardCountBlackjackApplication(){}

    public static void main(String[] args) {
        SpringApplication.run(CardCountBlackjackApplication.class, args);
    }

}
