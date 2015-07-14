package p.ripper.reader;

import java.io.File;

import p.ripper.IReader.IFileReader;

public class Excel2003Reader implements IFileReader {

	public String reader(File file) throws Exception {
		return ExcelReader.get2003AllSheetData(file.getAbsolutePath());
	}

}
