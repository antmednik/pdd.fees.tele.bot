package io.traffic.offences.fees.bot.data;

import io.traffic.offences.fees.bot.domain.TrafficOffence;

import java.util.ArrayList;
import java.util.List;

public class TrafficOffencesParser {

    private static final String ARTICLE_LINE_SEPARATOR = "---";

    private final List<TrafficOffence> trafficOffences;
    private TrafficOffence.Builder currentTrafficOffenceBuilder;

    public TrafficOffencesParser() {
        this.trafficOffences = new ArrayList<>();
    }

    public void add(String line) {
        if (currentTrafficOffenceBuilder == null) {
            currentTrafficOffenceBuilder = new TrafficOffence.Builder().number(line);
        } else if (currentTrafficOffenceBuilder.title() == null) {
            currentTrafficOffenceBuilder.title(line);
        } else if (ARTICLE_LINE_SEPARATOR.equals(line)) {
            trafficOffences.add(currentTrafficOffenceBuilder.trafficOffence());
            currentTrafficOffenceBuilder = null;
        } else {
            currentTrafficOffenceBuilder.addTextLine(line);
        }
    }

    public List<TrafficOffence> articles() {
        if (currentTrafficOffenceBuilder != null) {
            throw new RuntimeException("Invalid use, traffic offence article building is not finished."); // todo
        }
        return trafficOffences;
    }
}
