package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	
	public static ArrayList<HashMap<String, String>> readXLSXFile(String path, String[] keylist) throws IOException {
		ArrayList<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();
		
		InputStream ExcelFileToRead = new FileInputStream(path);
		
		//create workbook instance refers to xlsx
		XSSFWorkbook workbook = new XSSFWorkbook(ExcelFileToRead);
		
		//create a sheet object to retrive the sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		//evaluate the cell type
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			Iterator<Cell> cellIterator = row.cellIterator();
			final int index = 0;
			while(cellIterator.hasNext()) {
				map.put(keylist[index], cellIterator.next().getStringCellValue());
			}
			maplist.add(map);
		}
		return maplist;
	}
}
