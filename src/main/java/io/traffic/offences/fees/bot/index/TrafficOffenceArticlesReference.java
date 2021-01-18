package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TrafficOffenceArticlesReference implements InitializingBean, DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(TrafficOffenceArticlesReference.class);

    private final TrafficOffenceArticlesIndexProvider indexProvider;
    private TrafficOffenceArticlesIndex index;

    public TrafficOffenceArticlesReference(TrafficOffenceArticlesIndexProvider indexProvider) {
        this.indexProvider = indexProvider;
    }

    @Override
    public void afterPropertiesSet()  {
        try {
            index = indexProvider.index();
        } catch (Exception ex) {
            LOG.error("Unable to load index.", ex);
        }
    }

    public List<TrafficOffenceArticle> search(String query) {
        return index().fuzzySearchTopTen(query);
    }

    @Override
    public void destroy() {
        if (index != null) {
            try {
                index.close();
            } catch (Exception ex) {
                LOG.error("Unable to close index.", ex);
            }
        }
    }

    private TrafficOffenceArticlesIndex index() {
        return Objects.requireNonNull(index);
    }
}
