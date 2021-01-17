package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffence;

import java.util.List;

public interface PddArticlesProvider {

    List<TrafficOffence> articles();
}
