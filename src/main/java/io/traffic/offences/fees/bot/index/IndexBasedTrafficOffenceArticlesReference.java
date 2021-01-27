package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class IndexBasedTrafficOffenceArticlesReference implements TrafficOffenceArticlesReference, InitializingBean, DisposableBean {
    private final TrafficOffenceArticlesIndexProvider indexProvider;
    private TrafficOffenceArticlesIndex index;

    public IndexBasedTrafficOffenceArticlesReference(TrafficOffenceArticlesIndexProvider indexProvider) {
        this.indexProvider = indexProvider;
    }

    @Override
    public void afterPropertiesSet()  {
        try {
            index = indexProvider.index();
        } catch (Exception ex) {
            log.error("Unable to load index.", ex);
        }
    }

    public List<TrafficOffenceArticle> search(String query) {
        return index().fuzzySearchTopResults(query);
    }

    @Override
    public TrafficOffenceArticle randomArticle() {
        return index().randomArticle();
    }

    @Override
    public TrafficOffenceArticle articleByNumber(String articleNumber) {
        return index().articleByNumber(articleNumber);
    }

    @Override
    public void destroy() {
        if (index != null) {
            try {
                index.close();
            } catch (Exception ex) {
                log.error("Unable to close index.", ex);
            }
        }
    }

    private TrafficOffenceArticlesIndex index() {
        return Objects.requireNonNull(index);
    }
}
