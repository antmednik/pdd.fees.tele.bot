package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;

import java.util.List;

public interface TrafficOffenceArticlesReference {

    List<TrafficOffenceArticle> search(String query);

    TrafficOffenceArticle randomArticle();

    TrafficOffenceArticle articleByNumber(String articleNumber);
}
