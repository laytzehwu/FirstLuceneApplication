package com.arvato.scp.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	private IndexWriter writer;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		// Directory which contains index files
		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		
		// Index config
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		
		// Create index
		writer = new IndexWriter(indexDirectory, config);
	}
	
	public void close() throws CorruptIndexException, IOException {
		//System.out.println("Close writer!!!!");
		// It is called when process end
		writer.close();
	}
	
	private Document getDocument(File file) throws IOException {
		Document doc = new Document();
		
		// Index file content
		TextField contentField = new TextField(LuceneConstants.CONTENTS, new FileReader(file));
		
		// index file name
		FieldType filenameType = new FieldType();
		filenameType.setStored(true);
		filenameType.setTokenized(false);
		Field filenameField = new Field(LuceneConstants.FILE_NAME, file.getName(), filenameType);
		
		// index path
		FieldType filePathType = new FieldType(filenameType);
		Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), filePathType);
		
		doc.add(contentField);
		doc.add(filenameField);
		doc.add(filePathField);
		
		return doc;
	}
	
	private void indexFile(File file) throws IOException{
		System.out.println("Indexing " + file.getCanonicalPath());
		Document doc = getDocument(file);
		writer.addDocument(doc);
	}
	
	public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
		// Retrieve all the file from the directory
		File[] files = new File(dataDirPath).listFiles();
		
		// Clean everything first
		writer.deleteAll();
		for(File file : files) {
			if (filter.accept(file)) {
				indexFile(file);
			}
		}
		writer.commit();
		
		return writer.numDocs();
	}
}
