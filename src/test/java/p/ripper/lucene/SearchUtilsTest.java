package p.ripper.lucene;

import org.junit.Before;
import org.junit.Test;

public class SearchUtilsTest {
	private SearchUtils searchUtils;
	@Before	
	public void init(){
		new IndexUtil().index();
		searchUtils=new SearchUtils();
	}
	
	@Test
	public void search(){
		searchUtils.searchByTerm("name", "zhangsan", 3);
	}
}
