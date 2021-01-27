package io.traffic.offences.fees.bot.domain;

import lombok.Getter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@Getter
public class TrafficOffenceArticle {

    private final String number;
    private final String title;
    private final String text;

    public TrafficOffenceArticle(String number, String title, String text) {
        this.number = Objects.requireNonNull(number);
        this.title = Objects.requireNonNull(title);
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Статья ");
        builder.append(number);
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(title);
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(text);
        return builder.toString();
    }
}
