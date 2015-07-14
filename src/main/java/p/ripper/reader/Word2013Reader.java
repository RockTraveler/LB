package p.ripper.reader;

import java.io.File;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import p.ripper.IReader.IFileReader;

public class Word2013Reader implements IFileReader {

	@SuppressWarnings("resource")
	public String reader(File file) throws Exception {
		return new XWPFWordExtractor(POIXMLDocument.openPackage(file.getAbsolutePath())).getText().trim();
	}

}
