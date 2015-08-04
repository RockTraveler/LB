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

	private static StringBuffer  sb = new StringBuffer();
	public void index() {
		IndexWriter writer = null;
		String paths=getDirectory(new File("D:\\test"));									//CONFIGGG
		for (String path : paths.split(",")) {
			doIndex(path,writer);
		}

	}

	private void doIndex(String path,IndexWriter writer) {
	
		try {
			Directory directory = FSDirectory.open(new File("D:\\index"));					//CONFIG
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

			Document doc = null;
			writer = new IndexWriter(directory, iwc);
			writer.deleteAll();
			File f = new File(path);													
			for (File file : f.listFiles()) {
				doc = new Document();
				Context ctx = new Context(file);
				if(file.isDirectory()) continue;
				String content =ctx.getFileReader().reader(file);
				System.out.println(content);
				doc.add(new Field("content", content, Field.Store.NO,
						Field.Index.ANALYZED));
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

	public void searcher(String searchContent) {
		IndexSearcher searcher = null;
		try {
			Directory directory = FSDirectory.open(new File("D:\\index"));			//CONFIG
			IndexReader indexReader = IndexReader.open(directory);
			searcher = new IndexSearcher(indexReader);
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse(searchContent);										
			TopDocs tds = searcher.search(query, 1);
			ScoreDoc[] scoreDocs = tds.scoreDocs;
				for ( ScoreDoc scoreDoc : scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				System.out.println(document.get("filename") + "  [" + document.get("path") + "]");
			}
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
	
    public static  String getDirectory(File file){
        if(file!=null){
            if(file.isDirectory()){
                File[] fileArray=file.listFiles();
                sb.append(file.getAbsolutePath()+",");
                if(fileArray!=null){
                    for (int i = 0; i < fileArray.length; i++) {
                        getDirectory(fileArray[i]);
                    }
                }
            }
        }
        return sb.toString();
    }
	
}
