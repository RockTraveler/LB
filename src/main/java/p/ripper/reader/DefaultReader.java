package p.ripper.reader;

import java.io.File;

import p.ripper.IReader.IFileReader;

public class DefaultReader implements IFileReader{

	public String reader(File file) {
		return "defaultContent";
	}

	
}
