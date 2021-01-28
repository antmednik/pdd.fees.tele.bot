package io.traffic.offences.fees.bot.polling;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;

@ConfigurationProperties(prefix = "traffic.offence.fees.bot.polling")
@Getter
@ConstructorBinding
public class PollingBotProperties {

    private final Duration timeout;

    @Min(1)
    @Max(100)
    private final Short updatesLimit;

    public PollingBotProperties(Duration timeout, Short updatesLimit) {
        this.timeout = timeout;
        this.updatesLimit = updatesLimit;
    }
}
