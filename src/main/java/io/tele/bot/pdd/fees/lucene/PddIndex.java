package io.tele.bot.pdd.fees.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class PddIndex {

    public void build() {
        /*todo Directory memoryIndex = new RAMDirectory();
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writter = new IndexWriter(memoryIndex, indexWriterConfig);
        Document document = new Document();

        document.add(new TextField("title", title, Field.Store.YES));
        document.add(new TextField("body", body, Field.Store.YES));

        writter.addDocument(document);
        writter.close();*/
    }
}
