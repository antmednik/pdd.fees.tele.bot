package io.tele.bot.pdd.fees.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PddArticle {

    private final String number;
    private final String title;
    private final String text;

    public PddArticle(String number, String title, String text) {
        this.number = Objects.requireNonNull(number);
        this.title = Objects.requireNonNull(title);
        this.text = Objects.requireNonNull(text);
    }

    public String number() {
        return number;
    }

    public String title() {
        return title;
    }

    public String text() {
        return text;
    }

    public static class Builder {
        private String number;
        private String title;
        private List<String> textLines;

        public Builder() {
            textLines = new ArrayList<>();
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public String title() {
            return title;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addTextLine(String textLine) {
            this.textLines.add(textLine);
            return this;
        }

        public PddArticle article() {
            return new PddArticle(number, title, text());
        }

        private String text() {
            return textLines.stream().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
