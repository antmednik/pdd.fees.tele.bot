package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericGetUpdatesResponse;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetUpdatesResponseToGenericConverter implements Converter<GetUpdatesResponse, GenericGetUpdatesResponse> {

    private final Converter<Update, GenericUpdate> genericUpdateConverter;

    @Override
    public GenericGetUpdatesResponse convert(GetUpdatesResponse source) {
        return new GenericGetUpdatesResponse(
                source.updates().stream().map(genericUpdateConverter::convert).collect(Collectors.toList()));
    }
}
