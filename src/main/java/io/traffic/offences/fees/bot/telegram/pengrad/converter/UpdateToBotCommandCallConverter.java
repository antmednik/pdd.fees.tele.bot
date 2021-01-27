package io.traffic.offences.fees.bot.telegram.pengrad.converter;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import io.traffic.offences.fees.bot.telegram.pengrad.exception.RawCommandParseException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class UpdateToBotCommandCallConverter implements Converter<Update, BotCommandCall> {

    @Override
    public BotCommandCall convert(Update source) {
        Message message = source.message();
        if (message == null) {
            throw new RawCommandParseException("Message is undefined.");
        }
        if (message.entities().length != 1) {
            throw new RawCommandParseException("Message format is invalid.");
        }
        if (message.entities()[0].type() != MessageEntity.Type.bot_command) {
            throw new RawCommandParseException("Message type is not bot command.");
        }
        return botCommandCall(message.text());
    }

    private BotCommandCall botCommandCall(String text) {
        if (text == null || text.isBlank()) {
            throw new RawCommandParseException("Message text is not defined.");
        }

        String[] parts = text.split("\\s+");
        if (parts.length == 0) {
            throw new RawCommandParseException("Message text is empty.");
        }

        String rawKeyword = parts[0];
        if (rawKeyword.length() < 2) {
            throw new RawCommandParseException("Invalid bot command call format.");
        }
        if (rawKeyword.charAt(0) != '/') {
            throw new RawCommandParseException("Invalid bot command call format.");
        }
        String keyword = rawKeyword.substring(1);
        String arguments = Arrays.stream(parts, 1, parts.length)
                .collect(Collectors.joining(" "));
        return new BotCommandCall(keyword, arguments);
    }
}
