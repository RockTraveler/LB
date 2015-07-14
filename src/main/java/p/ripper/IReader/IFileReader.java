package p.ripper.IReader;

import java.io.File;

public interface IFileReader {
	//define a reader which implement method could be use for reading a file
	String reader(File file) throws Exception;

}
