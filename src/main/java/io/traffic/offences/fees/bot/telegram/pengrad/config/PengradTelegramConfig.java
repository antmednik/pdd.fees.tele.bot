package io.traffic.offences.fees.bot.telegram.pengrad.config;

import com.pengrad.telegrambot.TelegramBot;
import io.traffic.offences.fees.bot.telegram.pengrad.properties.PengradTelegramApiClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PengradTelegramConfig {

    @Bean
    public TelegramBot telegramBot(PengradTelegramApiClientProperties properties) {
        return new TelegramBot(properties.getToken());
    }
}
