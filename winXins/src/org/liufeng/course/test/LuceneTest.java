package org.liufeng.course.test;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Lucene�Ļ���ʹ��ʾ��
 * 
 * @author liufeng
 * @date 2013-12-1
 */
public class LuceneTest {
	// �����洢λ��
	private String indexDir = "C:/indexDir";
	// Field����
	private String fieldName = "content";

	/**
	 * ��������
	 * 
	 * @param analyzer �ִ���
	 * @throws IOException
	 */
	public void createIndex(Analyzer analyzer) throws IOException {
		// ���������ı�����
		String[] contentArr = { 
			"�����廪����������˵�����", 
			"�廪���й������ߵ�ѧ��", 
			"�廪��ѧ���������������Ĵ�ѧ֮һ"
		};
		// �����������Ŀ¼
		Directory directory = FSDirectory.open(new File(indexDir));
		// ����IndexWriter
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, conf);
		// �������鴴������
		for (String text : contentArr) {
			// ����document�����field
			Document document = new Document();
			document.add(new TextField(fieldName, text, Field.Store.YES));
			// ��document��ӵ�������
			indexWriter.addDocument(document);
		}
		indexWriter.commit();
		indexWriter.close();
		directory.close();
	}

	/**
	 * �������м���
	 * 
	 * @param sentence �������
	 * @param analyzer �ִ���
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void searchIndex(String sentence, Analyzer analyzer) throws Exception {
		// �����������Ŀ¼
		Directory directory = FSDirectory.open(new File(indexDir));
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		// ʹ�ò�ѯ����������Query
		QueryParser parser = new QueryParser(Version.LUCENE_46, fieldName, analyzer);
		Query query = parser.parse(sentence);
		// ���������Ĳ�ѯ���
		System.out.println("��ѯ��䣺" + query.toString());
		// �������������÷�����ǰ10���ĵ�
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		System.out.println("��������" + topDocs.totalHits + "��ƥ����");
		for (ScoreDoc sd : scoreDoc) {
			// ����id��ȡdocument
			Document d = searcher.doc(sd.doc);
			System.out.println(d.get(fieldName) + " score:" + sd.score);
			// �鿴�ĵ��÷ֽ���
			System.out.println(searcher.explain(query, sd.doc));
		}
		reader.close();
		directory.close();
	}

	public static void main(String[] args) throws Exception {
		// �����ִ���
		Analyzer analyzer = new IKAnalyzer(true);

		LuceneTest luceneTest = new LuceneTest();
		// ��������
		luceneTest.createIndex(analyzer);
		// �������м���
		luceneTest.searchIndex("�������廪", analyzer);
	}
}