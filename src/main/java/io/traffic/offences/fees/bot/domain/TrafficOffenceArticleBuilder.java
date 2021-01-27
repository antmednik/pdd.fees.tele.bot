package io.traffic.offences.fees.bot.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrafficOffenceArticleBuilder {
    private String number;
    private String title;
    private final List<String> textLines;

    public TrafficOffenceArticleBuilder() {
        textLines = new ArrayList<>();
    }

    public TrafficOffenceArticleBuilder number(String number) {
        this.number = number;
        return this;
    }

    public String title() {
        return title;
    }

    public void title(String title) {
        this.title = title;
    }

    public void addTextLine(String textLine) {
        textLines.add(textLine);
    }

    public TrafficOffenceArticle trafficOffence() {
        return new TrafficOffenceArticle(number, title, text());
    }

    private String text() {
        return textLines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
