package io.traffic.offences.fees.bot.telegram.pengrad.converters;

import com.pengrad.telegrambot.request.GetUpdates;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericGetUpdatesRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenericToGetUpdatesConverter implements Converter<GenericGetUpdatesRequest, GetUpdates> {

    @Override
    public GetUpdates convert(GenericGetUpdatesRequest source) {
        final var getUpdatesRequest = new GetUpdates()
                .offset(source.getOffset())
                .limit(source.getLimit());
        source.getTimeoutInSeconds().ifPresent(getUpdatesRequest::timeout);
        return getUpdatesRequest;
    }
}
