package p.ripper.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import p.ripper.IReader.IFileReader;

public class DefaultReader implements IFileReader {

	public String reader(File file) {
		
		return readTxtFile(file.getAbsolutePath());
	}

	private static String readTxtFile(String filePath) {
		StringBuffer sb = new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
