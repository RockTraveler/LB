package p.ripper.lucene;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class IndexUtil {
	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@itat.org", "bb.itat.org", "cc@cc.org", "dd@sina.com", "ee@zt.org", "ff@zt.org" };
	private String[] content = { "welcome visited the space, I like book", "Hello boy , I like pingpeng", "My name is cc, I like game", "I like football", "I like football and I like basketball too",
			"I like movie and swim" };
	private int[] attachs = { 2, 3, 1, 4, 5, 5 };
	private String[] name = { "zhangsan", "lisi", "john", "jetty", "jake", "wangwu" };
	private Directory directory = null;
	private Map<String, Float> scores = new HashMap<String, Float>();
	private Date[] dates = null;
	private IndexReader reader = null;
	private IndexSearcher searcher = null;

	// create the construct method for init this directory.
	IndexUtil() {
		try {
			setDates();
			scores.put("itat.org", 2.0f);
			scores.put("zt.org", 1f);
			directory = FSDirectory.open(new File("D:/lucene/index02"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void index() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();
			Document doc = null;
			for (int i = 0; i < ids.length; i++) {
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES, Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES, Index.NOT_ANALYZED));
				doc.add(new Field("content", content[i], Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("name", name[i], Field.Store.YES, Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new NumericField("attach", Field.Store.YES, true).setIntValue(attachs[i]));
				doc.add(new NumericField("dates", Field.Store.YES, true).setLongValue(dates[i].getTime()));
				writer.addDocument(doc);
			}

		} catch (Exception e) {
			try {
				writer.close();
			} catch (CorruptIndexException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				writer.close();
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void update() {
		// Lucence's update inculding two operations which are delete and
		// rewrite the index

	}

	public void query() {
		try {
			getReader();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteDocuments(new Term("id", "1"));

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void undoDelete() {
		try {
			getReader();
			reader.undeleteAll();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleleByForce() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.forceMergeDeletes();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void merge() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			// will merge index to two segement ,will delete the data which is
			// be delete
			// pay attention , do not use this method after 3.5 , the reason why
			// this operation will be get more cpu and memory
			writer.forceMerge(2);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void search() {

		try {
			getReader();
			searcher = new IndexSearcher(reader);
			TermQuery query = new TermQuery(new Term("content", "like"));
			TopDocs tds = searcher.search(query, 10);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
			//	System.out.println(doc.get("id") + "---->" + doc.get("name") + "[" + doc.get("email") + "]-->" + doc.get("id") + "," + doc.get("attach") + "," + doc.get("dates"));
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				reader.close();
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void getReader() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory,false);
			} else {
				IndexReader tr = IndexReader.openIfChanged(reader, false);
				if (tr != null) {
					reader = tr;
				}
			}

		} catch (Exception e) {
		}

	}

	private void setDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dates = new Date[ids.length];
		try {
			dates[0] = sdf.parse("2010-02-19");
			dates[1] = sdf.parse("2012-01-11");
			dates[2] = sdf.parse("2011-09-19");
			dates[3] = sdf.parse("2010-12-22");
			dates[4] = sdf.parse("2012-01-01");
			dates[5] = sdf.parse("2011-05-19");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void deleteByReader() {
		getReader();
		try {
			reader.deleteDocuments(new Term("id", "1"));
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
