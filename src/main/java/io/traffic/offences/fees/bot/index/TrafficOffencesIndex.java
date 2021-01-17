package io.traffic.offences.fees.bot.index;

import io.traffic.offences.fees.bot.domain.TrafficOffence;
import io.traffic.offences.fees.bot.index.exception.TrafficOffencesIndexBuildException;
import io.traffic.offences.fees.bot.index.exception.TrafficOffencesIndexIllegalOperationException;
import io.traffic.offences.fees.bot.index.exception.TrafficOffencesIndexSearchException;
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

/**
 * Functions:
 * 1. Index traffic rules
 * 2. Search top 10 results by exact query
 * 3. Search all results with pagination by exact query
 * 4. Search top 10 results by fuzzy query
 * 5. Search all results with pagination by fuzzy query
 */
public class TrafficOffencesIndex implements AutoCloseable {

    private static final int LIMIT = 10;

    private final Directory memoryIndex;
    private final StandardAnalyzer analyzer;
    private IndexReader indexReader;

    public TrafficOffencesIndex() {
        memoryIndex = new RAMDirectory();
        analyzer = new StandardAnalyzer();
    }

    public void build(List<TrafficOffence> articles) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(memoryIndex, indexWriterConfig)) {
            articles.stream()
                    .map(TrafficOffenceDocumentWrapper::new)
                    .forEach(wrapper -> write(writer, wrapper));
        } catch (IOException e) {
            throw new TrafficOffencesIndexBuildException("Unable to build index.", e);
        }

        try {
            indexReader = DirectoryReader.open(memoryIndex);
        } catch (IOException e) {
            throw new TrafficOffencesIndexBuildException("Unable to open reader for built index.", e);
        }
    }


    public List<TrafficOffence> searchTopTen(String query) {
        Query queryObj;
        try {
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(TrafficOffenceDocumentWrapper.fields(), analyzer);
            queryObj = queryParser.parse(query);
        } catch (ParseException e) {
            throw new TrafficOffencesIndexSearchException("Error while parsing query", e);
        }

        return searchTopTen(queryObj);
    }

    public List<TrafficOffence> fuzzySearchTopTen(String query) {
        Query queryObj = new BooleanQuery.Builder()
                .add(new FuzzyQuery(TrafficOffenceDocumentWrapper.FieldTerm.number(query)), BooleanClause.Occur.SHOULD)
                .add(new FuzzyQuery(TrafficOffenceDocumentWrapper.FieldTerm.title(query)), BooleanClause.Occur.SHOULD)
                .add(new FuzzyQuery(TrafficOffenceDocumentWrapper.FieldTerm.text(query)), BooleanClause.Occur.SHOULD)
                .build();

        return searchTopTen(queryObj);
    }

    public List<TrafficOffence> searchTopTen(Query query) {
        try {
            IndexSearcher searcher = new IndexSearcher(indexReader());
            TopDocs topDocs = searcher.search(query, LIMIT);

            List<TrafficOffence> trafficOffences = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                TrafficOffenceDocumentWrapper wrapper = new TrafficOffenceDocumentWrapper(searcher.doc(scoreDoc.doc));
                trafficOffences.add(wrapper.trafficRule());
            }
            return trafficOffences;
        } catch (IOException e) {
            throw new TrafficOffencesIndexSearchException("Error while executing query", e);
        }
    }

    private void write(IndexWriter writer, TrafficOffenceDocumentWrapper wrapper) {
        try {
            writer.addDocument(wrapper.document());
        } catch (IOException e) {
            throw new RuntimeException(e);  // todo: change to custom exception type
        }
    }

    private IndexReader indexReader() {
        if (indexReader == null) {
            throw new TrafficOffencesIndexIllegalOperationException("Index was not built.");
        }

        return indexReader;
    }

    @Override
    public void close() throws Exception {
        memoryIndex.close();
        analyzer.close();
    }
}
