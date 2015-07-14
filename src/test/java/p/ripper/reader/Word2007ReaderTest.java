package p.ripper.reader;

import java.io.File;

import org.junit.Test;

public class Word2007ReaderTest {
	
	Word2007Reader word2007Reader =null;
	public Word2007ReaderTest() {
		this.word2007Reader = new Word2007Reader();
	}
	
	@Test
	public void readerTest() throws Exception{
		this.word2007Reader.reader(new File("D:/lucene/example/2013.xlsx"));
	}

}
