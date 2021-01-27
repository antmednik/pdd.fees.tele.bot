package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.Message;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToGenericConverter implements Converter<Message, GenericMessage> {

    @Override
    public GenericMessage convert(Message source) {
        return new GenericMessage(
                source.messageId(),
                source.chat() == null ? null : source.chat().id(),
                source.text());
    }
}
