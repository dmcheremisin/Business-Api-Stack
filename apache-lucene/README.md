# Simple Apache Lucene project

### How to?

1. Add files to a data directory. By default there are some content about Hunger Games book.

2. A search query is located in the LuceneFileSearch.java.

luceneFileSearch.searchFiles("contents", "Rues")

3. Build and run the project. There will be additional folder with index where lucene will put all
its indexes. And the program will output a result for your search.

### Tech:
* Java 8
* Maven 3.5.4
* Apache lucene 7.1.0