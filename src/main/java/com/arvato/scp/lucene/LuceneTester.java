package com.arvato.scp.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	Indexer indexer;
	Searcher searcher;
	
	public static void main(String arg[]) {
		LuceneTester tester;
		try {
			tester = new LuceneTester();
			tester.createIndex();
			tester.search("BizTalk2LuceneExporter");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void createIndex() throws IOException {
		indexer = new Indexer(LuceneConstants.INDEX_DIR);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		numIndexed = indexer.createIndex(LuceneConstants.DATA_DIR, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println(numIndexed + " file indexed, time taken: " + (endTime - startTime) + " ms");
	}
	
	private void search(String searchQuery) throws IOException, ParseException {
		searcher = new Searcher(LuceneConstants.INDEX_DIR);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();
		searcher.close();
		System.out.println(hits.totalHits + " documents found. Time: " + (endTime - startTime) + " ms");
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
		}
	}
}
