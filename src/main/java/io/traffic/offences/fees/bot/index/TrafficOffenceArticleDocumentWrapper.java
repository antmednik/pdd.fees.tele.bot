package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;

public class TrafficOffenceArticleDocumentWrapper {

    public static class FieldTerm {
        private FieldTerm() {
        }

        public static Term number(String text) {
            return new Term(Field.number(), text);
        }
        public static Term title(String text) {
            return new Term(Field.title(), text);
        }
        public static Term text(String text) {
            return new Term(Field.text(), text);
        }
    }

    public static class Field {
        private Field() {
        }

        public static String number() { return "number"; }
        public static String title() { return "title"; }
        public static String text() { return "text"; }
        public static String[] all() { return new String[]{number(), title(), text()}; }
    }

    private final Document document;

    public TrafficOffenceArticleDocumentWrapper(Document document) {
        this.document = document;
    }

    public TrafficOffenceArticleDocumentWrapper(TrafficOffenceArticle article) {
        document = new Document();
        document.add(new TextField(Field.number(), article.getNumber(), org.apache.lucene.document.Field.Store.YES));
        document.add(new TextField(Field.title(), article.getTitle(), org.apache.lucene.document.Field.Store.YES));
        document.add(new TextField(Field.text(), article.getText(), org.apache.lucene.document.Field.Store.YES));
    }

    public String number() {
        return document.getField(Field.number()).stringValue();
    }

    public Document document() {
        return document;
    }

    public TrafficOffenceArticle trafficRule() {
        return new TrafficOffenceArticle(
                number(),
                document.getField(Field.title()).stringValue(),
                document.getField(Field.text()).stringValue());
    }
}
