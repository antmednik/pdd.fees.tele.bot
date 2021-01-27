package io.traffic.offences.fees.bot.telegram.pengrad.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "traffic.offence.fees.telegram.pengrad")
@Getter
@ConstructorBinding
public class PengradTelegramApiClientProperties {

    private final String token;

    public PengradTelegramApiClientProperties(String token) {
        this.token = token;
    }
}
