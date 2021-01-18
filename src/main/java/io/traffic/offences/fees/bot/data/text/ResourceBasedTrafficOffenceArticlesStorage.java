package io.traffic.offences.fees.bot.data.text;

import io.traffic.offences.fees.bot.data.TrafficOffenceArticlesStorage;
import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import io.traffic.offences.fees.bot.index.exception.TrafficOffenceArticlesIndexBuildException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ResourceBasedTrafficOffenceArticlesStorage implements TrafficOffenceArticlesStorage {

    private static final String ARTICLES_RESOURCE_FILE_NAME = "traffic-offences.txt";

    @Override
    public List<TrafficOffenceArticle> articles() {
        try (InputStream stream = articlesDataStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                TrafficOffenceArticlesParser parser = new TrafficOffenceArticlesParser();
                reader.lines().forEach(parser::add);
                return parser.articles();
            }
        } catch (IOException e) {
            throw new TrafficOffenceArticlesIndexBuildException("Unable to read traffic offences data.", e);
        }
    }

    private InputStream articlesDataStream() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(ARTICLES_RESOURCE_FILE_NAME);
        if (stream == null) {
            throw new TrafficOffenceArticlesIndexBuildException("Unable to find traffic offences data file.");
        }
        return stream;
    }
}
