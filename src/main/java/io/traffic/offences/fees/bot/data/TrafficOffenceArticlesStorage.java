package io.traffic.offences.fees.bot.data;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;

import java.util.List;

public interface TrafficOffenceArticlesStorage {

    List<TrafficOffenceArticle> articles();
}
