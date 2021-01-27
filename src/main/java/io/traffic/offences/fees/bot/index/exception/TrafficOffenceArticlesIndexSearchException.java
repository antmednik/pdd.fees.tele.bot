package io.traffic.offences.fees.bot.index.exception;

public class TrafficOffenceArticlesIndexSearchException extends RuntimeException {

    public TrafficOffenceArticlesIndexSearchException(String message) {
        super(message);
    }

    public TrafficOffenceArticlesIndexSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
