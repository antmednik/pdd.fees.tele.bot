package io.traffic.offences.fees.bot.telegram.generic.dto;

import lombok.Getter;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault

public class GenericMessage {
    @Getter
    private final Integer messageId;
    private final Long chatId;
    @Getter
    private final String text;

    public GenericMessage(Integer messageId, @Nullable Long chatId, String text) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.text = text;
    }

    public Optional<Long> getChatId() {
        return Optional.ofNullable(chatId);
    }
}
