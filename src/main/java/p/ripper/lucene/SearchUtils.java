package p.ripper.lucene;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchUtils {

	private Directory directory;
	private IndexReader reader;
	
	
	public SearchUtils() {
/*		directory=new RAMDirectory();*/
		try {
			directory=FSDirectory.open(new File("D:/lucene/index02"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public IndexSearcher getSearcher(){
		try {
			if (reader==null) {
				reader=IndexReader.open(directory);
			}else {
				IndexReader tr=IndexReader.openIfChanged(reader);
				if(tr!=null){
					reader.clone();
					reader=tr;
				}
			}
			return new IndexSearcher(reader);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	public void searchByTerm(String field,String name,int num){
		IndexSearcher searcher=getSearcher();
		Query query = new TermQuery(new Term(field,name));
		//Query query = NumericRangeQuery.newIntRange(field, min, max, minInclusive, maxInclusive)
		try {
			TopDocs tds=searcher.search(query, num);
			System.out.println("一共查询了:"+tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc=searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name") + "[" + doc.get("email") + "]-->" + doc.get("id") + "," + doc.get("attach") + "," + doc.get("dates"));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
