package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;

public class TrafficOffenceArticleDocumentWrapper {

    public static class FieldTerm {
        public static Term number(String text) {
            return new Term(FIELD_NAME_NUMBER, text);
        }

        public static Term title(String text) {
            return new Term(FIELD_NAME_TITLE, text);
        }

        public static Term text(String text) {
            return new Term(FIELD_NAME_TEXT, text);
        }
    }

    private static final String FIELD_NAME_NUMBER = "number";
    private static final String FIELD_NAME_TITLE = "title";
    private static final String FIELD_NAME_TEXT = "text";

    private final Document document;

    public static String[] fields() {
        return new String[]{FIELD_NAME_NUMBER, FIELD_NAME_TITLE, FIELD_NAME_TEXT};
    }

    public TrafficOffenceArticleDocumentWrapper(Document document) {
        this.document = document;
    }

    public TrafficOffenceArticleDocumentWrapper(TrafficOffenceArticle article) {
        document = new Document();
        document.add(new TextField(FIELD_NAME_NUMBER, article.number(), Field.Store.YES));
        document.add(new TextField(FIELD_NAME_TITLE, article.title(), Field.Store.YES));
        document.add(new TextField(FIELD_NAME_TEXT, article.text(), Field.Store.YES));
    }

    public String number() {
        return document.getField(FIELD_NAME_NUMBER).stringValue();
    }

    public Document document() {
        return document;
    }

    public TrafficOffenceArticle trafficRule() {
        return new TrafficOffenceArticle(
                number(),
                document.getField(FIELD_NAME_TITLE).stringValue(),
                document.getField(FIELD_NAME_TEXT).stringValue());
    }
}
