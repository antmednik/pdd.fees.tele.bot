package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.data.TrafficOffenceArticlesStorage;
import org.springframework.stereotype.Service;

@Service
public class TrafficOffenceArticlesIndexProvider {

    private final TrafficOffenceArticlesStorage trafficOffenceArticlesStorage;

    public TrafficOffenceArticlesIndexProvider(TrafficOffenceArticlesStorage trafficOffenceArticlesStorage) {
        this.trafficOffenceArticlesStorage = trafficOffenceArticlesStorage;
    }

    public TrafficOffenceArticlesIndex index() {
        TrafficOffenceArticlesIndex index = new TrafficOffenceArticlesIndex();
        index.build(trafficOffenceArticlesStorage.articles());
        return index;
    }
}
