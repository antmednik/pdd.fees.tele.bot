package io.traffic.offences.fees.bot.telegram.generic;

public class TelegramApiOpException extends RuntimeException {

    public TelegramApiOpException(String message) {
        super(message);
    }
}
