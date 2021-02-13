package io.traffic.offences.fees.bot.formatting;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficOffenceArticleFormatter {

    public String format(TrafficOffenceArticle article) {
        StringBuilder builder = new StringBuilder("<b><i><u>Статья ");
        builder.append(article.getNumber());
        builder.append("</u> ");
        builder.append(article.getTitle());
        builder.append("</i></b>");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(article.getText());
        return builder.toString();
    }

    public String format(List<TrafficOffenceArticle> candidates) {
        StringBuilder builder = new StringBuilder();
        for (var candidate : candidates) {
            builder.append("<b><i><u>Статья ");
            builder.append(candidate.getNumber());
            builder.append("</u> ");
            builder.append(candidate.getTitle());
            builder.append("</i></b>");
            builder.append(System.lineSeparator());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
