package org.lucene.demo;

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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LuceneFileSearch {
    private static final String DATA_DIR = "data/";
    private static final String INDEX_DIR = "index/";

    private Directory indexDirectory;
    private StandardAnalyzer analyzer;

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path indexPath = Paths.get(INDEX_DIR);
        cleanIndexDir(indexPath);

        Directory directory = FSDirectory.open(indexPath);
        LuceneFileSearch luceneFileSearch = new LuceneFileSearch(directory, new StandardAnalyzer());

        luceneFileSearch.index(DATA_DIR);

        List<Document> docs = luceneFileSearch.searchFiles("contents", "Rues");
        docs.forEach(doc -> System.out.println(doc.get("path")));
        // Output: data\hunger_games.txt
        // Rues was mentioned only in first book, so everything is correct
    }

    private static void cleanIndexDir(Path indexPath) {
        File[] files = indexPath.toFile().listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public LuceneFileSearch(Directory fsDirectory, StandardAnalyzer analyzer) {
        this.indexDirectory = fsDirectory;
        this.analyzer = analyzer;
    }

    public void index(String dataDir) throws IOException, URISyntaxException {
        Predicate<File> tester = f ->
                !f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && f.getName().toLowerCase().endsWith(".txt");

        File[] files = new File(dataDir).listFiles();
        for (File file : files) {
            if (tester.test(file)) {
                addFileToIndex(file);
            }
        }
    }

    public void addFileToIndex(File file) throws IOException, URISyntaxException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
        Document document = new Document();

        FileReader fileReader = new FileReader(file);
        document.add(new TextField("contents", fileReader));
        document.add(new StringField("path", file.getPath(), Field.Store.YES));
        document.add(new StringField("filename", file.getName(), Field.Store.YES));

        indexWriter.addDocument(document);

        indexWriter.close();
    }

    public List<Document> searchFiles(String inField, String queryString) {
        try {
            Query query = new QueryParser(inField, analyzer).parse(queryString);

            IndexReader indexReader = DirectoryReader.open(indexDirectory);
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