package p.ripper.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This Method could be reading Excel2003 , 2007
 * @author RockTraveler
 *
 */
public class ExcelReader {
	static Workbook wb = null;


	public ExcelReader(String path) {
		try {
			InputStream inp = new FileInputStream(path);
			wb = WorkbookFactory.create(inp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reading all of the excel data within the header
	 * 
	 * @return List<String[]>
	 */
	public static List<String[]> getAllData(int sheetIndex) {
		 List<String[]> dataList = new ArrayList<String[]>();
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			for (Row row : sheet) {
				String[] singleRow = new String[columnNum];
				int n = 0;
				for (int i = 0; i < columnNum; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							singleRow[n] = String.valueOf(cell.getDateCellValue());
						} else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String temp = cell.getStringCellValue();
							if (temp.indexOf(".") > -1) {
								singleRow[n] = String.valueOf(new Double(temp)).trim();
							} else {
								singleRow[n] = temp.trim();
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singleRow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singleRow[n] = cell.getStringCellValue();
						if (singleRow[n] != null) {
							singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
						}
						break;
					default:
						singleRow[n] = "";
						break;
					}
					n++;
				}
				dataList.add(singleRow);
			}
		}
		return dataList;
	}

	@SuppressWarnings("resource")
	public static String get2007_2013AllSheetData(String fileName) throws Exception{
		new ExcelReader(fileName);
		XSSFWorkbook xs = new XSSFWorkbook(new File(fileName)); 
		return getAllSheetData(xs.getNumberOfSheets());
	}
	
	@SuppressWarnings("resource")
	public static String get2003AllSheetData(String fileName) throws Exception{
        new ExcelReader(fileName );
        FileInputStream finput = new FileInputStream(fileName );
        HSSFWorkbook hs = new HSSFWorkbook(finput);
		return getAllSheetData(hs.getNumberOfSheets());
	}

	private static String getAllSheetData(int sheetLength) {
	     StringBuffer sb = new StringBuffer();
		for (int i = 0; i <sheetLength; i++) {
			List<String[]> list =ExcelReader.getAllData(i);
			for(String[] ss:list){
				for(String s:ss){
					sb.append(s);
				}
			}
		}
		//System.out.println(sb);
		return sb.toString();
	}
}
