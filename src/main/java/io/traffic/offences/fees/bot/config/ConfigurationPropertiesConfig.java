package io.traffic.offences.fees.bot.config;

import io.traffic.offences.fees.bot.telegram.pengrad.properties.PengradTelegramApiClientProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackageClasses = {PengradTelegramApiClientProperties.class})
public class ConfigurationPropertiesConfig {
}
