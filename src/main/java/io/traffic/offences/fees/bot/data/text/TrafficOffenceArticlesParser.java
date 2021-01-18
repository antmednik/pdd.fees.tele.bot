package io.traffic.offences.fees.bot.data.text;

import io.traffic.offences.fees.bot.data.exception.TrafficOffenceArticlesParserIllegalOperationException;
import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;

import java.util.ArrayList;
import java.util.List;

public class TrafficOffenceArticlesParser {

    private static final String ARTICLE_LINE_SEPARATOR = "---";

    private final List<TrafficOffenceArticle> articles;
    private TrafficOffenceArticle.Builder currentArticleBuilder;

    public TrafficOffenceArticlesParser() {
        this.articles = new ArrayList<>();
    }

    public void add(String line) {
        if (currentArticleBuilder == null) {
            currentArticleBuilder = new TrafficOffenceArticle.Builder().number(line);
        } else if (currentArticleBuilder.title() == null) {
            currentArticleBuilder.title(line);
        } else if (ARTICLE_LINE_SEPARATOR.equals(line)) {
            articles.add(currentArticleBuilder.trafficOffence());
            currentArticleBuilder = null;
        } else {
            currentArticleBuilder.addTextLine(line);
        }
    }

    public List<TrafficOffenceArticle> articles() {
        if (currentArticleBuilder != null) {
            throw new TrafficOffenceArticlesParserIllegalOperationException("Traffic offence article building is not finished.");
        }
        return articles;
    }
}
