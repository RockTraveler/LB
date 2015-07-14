package p.ripper.reader;

import java.io.File;

import p.ripper.IReader.IFileReader;

public class Excel2007Reader implements IFileReader {

	public String reader(File file) throws Exception {
		return ExcelReader.get2007_2013AllSheetData(file.getAbsolutePath());
	}

}
