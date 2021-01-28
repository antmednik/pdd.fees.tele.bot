package io.traffic.offences.fees.bot.persistence.view;

import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateView {
    private final Integer updateId;
    private final Long chatId;
    private final Integer messageId;

    public UpdateView(Integer updateId, Long chatId, Integer messageId) {
        this.updateId = Objects.requireNonNull(updateId);
        this.chatId = Objects.requireNonNull(chatId);
        this.messageId = Objects.requireNonNull(messageId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateView that = (UpdateView) o;
        return updateId.equals(that.updateId) && chatId.equals(that.chatId) && messageId.equals(that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateId, chatId, messageId);
    }
}
