package org.lucenceDemo;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Demo {
 
	@Test
	public void write() throws Exception{
         FSDirectory dy = FSDirectory.open(Paths.get("D:\\lucence01"));   
		 IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
		 
		IndexWriter iw = new IndexWriter(dy, conf);
		Document doc = new Document();
		Field fd1 = new TextField("title", "text doc", Store.YES);
		Field fd2 = new TextField("body", "text doc body", Store.YES);
		StoredField fd3 = new StoredField("footer", "text doc footer");
		doc.add(fd1);
		doc.add(fd2);
		doc.add(fd3);
		iw.addDocument(doc);
		iw.close();
	 }
	
	
	@Test
	public void search() throws Exception{
		    FSDirectory dy = FSDirectory.open(Paths.get("D:\\lucence01"));
			IndexReader r=DirectoryReader.open(dy);
			IndexSearcher is = new IndexSearcher(r);
			String[] strs = {"title","body","footer"};
		    MultiFieldQueryParser mqp = new MultiFieldQueryParser(strs,new StandardAnalyzer());
			//QueryParser qp = new QueryParser("title", new StandardAnalyzer());
		    Query query= mqp.parse("doc");
			//Query query = qp.parse("doc");
			ScoreDoc[] sd = is.search(query, 10).scoreDocs;
			for (ScoreDoc sd1 : sd) {
				  Document doc = is.doc(sd1.doc);
				  System.out.println("标题为 :"+doc.get("title"));
				  System.out.println("内容为 :"+doc.get("body"));
			}
	}
	
	
}
