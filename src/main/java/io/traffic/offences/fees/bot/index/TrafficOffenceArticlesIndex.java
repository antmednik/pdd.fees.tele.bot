package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import io.traffic.offences.fees.bot.index.exception.TrafficOffenceArticlesIndexBuildException;
import io.traffic.offences.fees.bot.index.exception.TrafficOffenceArticlesIndexIllegalOperationException;
import io.traffic.offences.fees.bot.index.exception.TrafficOffenceArticlesIndexSearchException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Functions:
 * 1. Index traffic rules
 * 2. Search top 10 results by exact query
 * 3. Search all results with pagination by exact query
 * 4. Search top 10 results by fuzzy query
 * 5. Search all results with pagination by fuzzy query
 */
class TrafficOffenceArticlesIndex implements AutoCloseable {

    private static final int LIMIT = 10;

    private final Directory memoryIndex;
    private final StandardAnalyzer analyzer;
    private Optional<IndexReader> indexReader;
    private final List<String> articleNumbers;
    private final Random random;

    public TrafficOffenceArticlesIndex() {
        memoryIndex = new RAMDirectory();   //NOSONAR
        analyzer = new StandardAnalyzer();
        indexReader = Optional.empty();
        articleNumbers = new ArrayList<>();
        random = new Random(System.currentTimeMillis());
    }

    public void build(List<TrafficOffenceArticle> articles) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(memoryIndex, indexWriterConfig)) {
            for (var article : articles) {
                articleNumbers.add(article.getNumber());
                var document = new TrafficOffenceArticleDocumentWrapper(article);
                write(writer, document);
            }
        } catch (IOException e) {
            throw new TrafficOffenceArticlesIndexBuildException("Unable to build index.", e);
        }

        try {
            indexReader = Optional.of(DirectoryReader.open(memoryIndex));
        } catch (IOException e) {
            throw new TrafficOffenceArticlesIndexBuildException("Unable to open reader for built index.", e);
        }
    }

    public List<TrafficOffenceArticle> searchTopResults(String query) {
        Query queryObj;
        try {
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(TrafficOffenceArticleDocumentWrapper.Field.all(), analyzer);
            queryObj = queryParser.parse(query);
        } catch (ParseException e) {
            throw new TrafficOffenceArticlesIndexSearchException("Error while parsing query", e);
        }

        return search(queryObj);
    }

    public List<TrafficOffenceArticle> fuzzySearchTopResults(String query) {
        Query queryObj = new BooleanQuery.Builder()
                .add(new FuzzyQuery(TrafficOffenceArticleDocumentWrapper.FieldTerm.number(query)), BooleanClause.Occur.SHOULD)
                .add(new FuzzyQuery(TrafficOffenceArticleDocumentWrapper.FieldTerm.title(query)), BooleanClause.Occur.SHOULD)
                .add(new FuzzyQuery(TrafficOffenceArticleDocumentWrapper.FieldTerm.text(query)), BooleanClause.Occur.SHOULD)
                .build();

        return search(queryObj);
    }

    public TrafficOffenceArticle randomArticle() {
        int randomArticleIndex = random.nextInt(articleNumbers.size());
        String articleNumber = articleNumbers.get(randomArticleIndex);
        return articleByNumber(articleNumber);
    }

    public TrafficOffenceArticle articleByNumber(String articleNumber) {
        Query queryObj;
        try {
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                    new String[] { TrafficOffenceArticleDocumentWrapper.Field.number() },
                    analyzer);
            queryObj = queryParser.parse(articleNumber);
        } catch (ParseException e) {
            throw new TrafficOffenceArticlesIndexSearchException("Error while parsing query", e);
        }

        List<TrafficOffenceArticle> candidates = search(queryObj);
        if (candidates.size() != 1) {
            throw new TrafficOffenceArticlesIndexSearchException(
                    String.format("Article with number '%s' not found.", articleNumber));
        }

        return candidates.get(0);
    }

    private List<TrafficOffenceArticle> search(Query query) {
        try {
            IndexSearcher searcher = new IndexSearcher(indexReader());
            TopDocs topDocs = searcher.search(query, LIMIT);

            List<TrafficOffenceArticle> trafficOffenceArticles = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                TrafficOffenceArticleDocumentWrapper wrapper = new TrafficOffenceArticleDocumentWrapper(searcher.doc(scoreDoc.doc));
                trafficOffenceArticles.add(wrapper.trafficRule());
            }
            return trafficOffenceArticles;
        } catch (IOException e) {
            throw new TrafficOffenceArticlesIndexSearchException("Error while executing query", e);
        }
    }

    private void write(IndexWriter writer, TrafficOffenceArticleDocumentWrapper wrapper) {
        try {
            writer.addDocument(wrapper.document());
        } catch (IOException e) {
            throw new TrafficOffenceArticlesIndexBuildException(
                    String.format("Unable to write traffic offence document with number '%s' in index.", wrapper.number()),
                    e);
        }
    }

    private IndexReader indexReader() {
        return indexReader.orElseThrow(() -> new TrafficOffenceArticlesIndexIllegalOperationException("Index was not built."));
    }

    @Override
    public void close() throws Exception {
        memoryIndex.close();
        analyzer.close();
    }
}
