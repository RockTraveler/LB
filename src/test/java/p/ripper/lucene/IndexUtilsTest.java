package p.ripper.lucene;


import org.junit.Test;

public class IndexUtilsTest {
	
	
	@Test
	public void deleteByForce() {
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.deleleByForce();
		

	}
	
	@Test
	public void query(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.query();
	}
	
	@Test
	public void index(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.index();
	}
	
	@Test
	public void delete(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.delete();
	}

	@Test
	public void undoDelete(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.undoDelete();
	}
	
	@Test
	public void TestSearch(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.search();
	}
	
	@Test
	public void testDeleteByReader(){
		IndexUtil indexUtil = new IndexUtil();
		indexUtil.deleteByReader();
	}
	
	
	
}
