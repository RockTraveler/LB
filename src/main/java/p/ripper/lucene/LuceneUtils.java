package p.ripper.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import p.ripper.context.Context;

public class LuceneUtils {

	public void index() {
		IndexWriter writer = null;
		try {
			Directory directory = FSDirectory.open(new File("D:/lucene/index01"));					//CONFIG
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

			Document doc = null;
			writer = new IndexWriter(directory, iwc);
			writer.deleteAll();
			File f = new File("D:/lucene/example");													//CONFIG
			for (File file : f.listFiles()) {
				doc = new Document();
				Context ctx = new Context(file);
				String content =ctx.getFileReader().reader(file);
				System.out.println(content);
				doc.add(new Field("content", content, Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
				writer.addDocument(doc);
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unused")
	public void searcher() {
		IndexSearcher searcher = null;
		try {
			Directory directory = FSDirectory.open(new File("D:/lucene/index01"));			//CONFIG
			IndexReader indexReader = IndexReader.open(directory);
			searcher = new IndexSearcher(indexReader);
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse("java");
			TopDocs tds = searcher.search(query, 10);
			ScoreDoc[] scoreDocs = tds.scoreDocs;
			/*	for ( ScoreDoc scoreDoc : scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				System.out.println(document.get("filename") + "  [" + document.get("path") + "]");
			}*/
			indexReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
