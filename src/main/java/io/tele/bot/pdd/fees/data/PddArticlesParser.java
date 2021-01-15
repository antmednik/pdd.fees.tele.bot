package io.tele.bot.pdd.fees.data;

import io.tele.bot.pdd.fees.lucene.PddArticle;

import java.util.ArrayList;
import java.util.List;

public class PddArticlesParser {

    private static final String ARTICLE_LINE_SEPARATOR = "---";

    private final List<PddArticle> articles;
    private PddArticle.Builder currentArticleBuilder;

    public PddArticlesParser() {
        this.articles = new ArrayList<>();
    }

    public void add(String line) {
        if (currentArticleBuilder == null) {
            currentArticleBuilder = new PddArticle.Builder().number(line);
        } else if (currentArticleBuilder.title() == null) {
            currentArticleBuilder.title(line);
        } else if (ARTICLE_LINE_SEPARATOR.equals(line)) {
            articles.add(currentArticleBuilder.article());
            currentArticleBuilder = null;
        } else {
            currentArticleBuilder.addTextLine(line);
        }
    }

    public List<PddArticle> articles() {
        if (currentArticleBuilder != null) {
            throw new RuntimeException("Invalid use, article building is not finished."); // todo
        }
        return articles;
    }
}
