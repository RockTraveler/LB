package p.ripper.context;

import java.io.File;

import p.ripper.IReader.IFileReader;
import p.ripper.reader.DefaultReader;
import p.ripper.reader.Excel2003Reader;
import p.ripper.reader.Excel2007Reader;
import p.ripper.reader.Word2003Reader;
import p.ripper.reader.Word2007Reader;

public class Context {

	private IFileReader fileReader;

	public Context(File file) {
		
		String extendName = getExtendName(file.getName());
		if(extendName.equals(".doc")){
			this.fileReader=new Word2003Reader();
		}else if(extendName.equals(".docx")){
			this.fileReader=new Word2007Reader();
		}else if(extendName.equals(".xls")){
			this.fileReader=new Excel2003Reader();
		}else if(extendName.equals(".xlsx")){
			this.fileReader=new Excel2007Reader();
		}else{
			this.fileReader=new DefaultReader();
		}
	}
	
	
	
	
	public static String getExtendName(String fileName) {
		
		return (String) (!fileName.equals("") && fileName != null && fileName.contains(".")
				? fileName.subSequence(fileName.lastIndexOf('.'), fileName.length()) : "");

	}
	
	public IFileReader getFileReader() {
		
		return fileReader;
	}

}
