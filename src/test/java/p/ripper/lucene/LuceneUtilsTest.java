package p.ripper.lucene;

public class LuceneUtilsTest {

	@org.junit.Test
	public void testIndex()
	{
		LuceneUtils h1 = new LuceneUtils();
		h1.index();
	}
	@org.junit.Test
	public void testSearch()
	{
		LuceneUtils h1 = new LuceneUtils();
		h1.searcher("red");
	}
	
	
}
