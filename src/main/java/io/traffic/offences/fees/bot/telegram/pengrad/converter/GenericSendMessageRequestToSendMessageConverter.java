package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericSendMessageRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenericSendMessageRequestToSendMessageConverter implements Converter<GenericSendMessageRequest, SendMessage> {

    @Override
    public SendMessage convert(GenericSendMessageRequest source) {
        return new SendMessage(source.getChatId(), source.getText())
                .parseMode(ParseMode.HTML);
    }
}
