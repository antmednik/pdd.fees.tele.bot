package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericMessage;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateToGenericConverter implements Converter<Update, GenericUpdate> {

    private final Converter<Message, GenericMessage> genericMessageConverter;

    @Override
    public GenericUpdate convert(Update source) {
        return new GenericUpdate(
                source.updateId(),
                genericMessageConverter.convert(source.message()));
    }
}
