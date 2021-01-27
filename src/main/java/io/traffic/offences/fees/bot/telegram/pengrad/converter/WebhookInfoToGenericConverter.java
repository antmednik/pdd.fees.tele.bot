package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.WebhookInfo;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericWebhookInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WebhookInfoToGenericConverter implements Converter<WebhookInfo, GenericWebhookInfo> {
    @Override
    public GenericWebhookInfo convert(WebhookInfo source) {
        return new GenericWebhookInfo(
                source.url(), source.hasCustomCertificate(), source.pendingUpdateCount()
        );
    }
}
