package io.traffic.offences.fees.bot.index.exception;

public class TrafficOffenceArticlesIndexBuildException extends RuntimeException {

    public TrafficOffenceArticlesIndexBuildException(String message) {
        super(message);
    }

    public TrafficOffenceArticlesIndexBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
