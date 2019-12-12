package com.business.api.search;

import com.business.api.model.Post;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class LuceneAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LuceneAdapter.class);

    private Path path;
    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private Directory directory;

    public LuceneAdapter() throws IOException {
        path = Paths.get("index/");
        this.directory = FSDirectory.open(path);
    }

    public void cleanIndexDir() {
        File[] files = path.toFile().listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public void addObjectToIndex(Post post) {
        try {
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            Document document = new Document();

            document.add(new StringField("id", post.getId().toString(), Field.Store.YES));
            document.add(new TextField("userId", post.getUserId().toString(), Field.Store.YES));
            document.add(new TextField("title", post.getTitle(), Field.Store.YES));
            document.add(new TextField("body", post.getBody(), Field.Store.YES));

            indexWriter.addDocument(document);

            indexWriter.close();
        } catch (Exception e) {
            logger.error("Can't object to index: " + post);
            logger.error(e.getMessage());
        }
    }

    public List<Document> searchFiles(String inField, String queryString) {
        try {
            Query query = new QueryParser(inField, analyzer).parse(queryString);

            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10);
            List<Document> documents = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                documents.add(searcher.doc(scoreDoc.doc));
            }
            return documents;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}
