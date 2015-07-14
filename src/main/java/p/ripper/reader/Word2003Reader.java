package p.ripper.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

import p.ripper.IReader.IFileReader;

public class Word2003Reader implements IFileReader {

	@SuppressWarnings("resource")
	public String reader(File f) throws Exception {
		return new WordExtractor(new FileInputStream(f)).getText().trim();
	}

}
