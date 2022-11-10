package genericLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtility {
	private Workbook workbook;
	
	public void excelFileInitialization(String excelPath)  {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(excelPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, String> fetchDataFromExcel(String expectedtestNAme, String sheetName)  {
		
		Sheet sheet = workbook.getSheet(sheetName);
		Map<String, String> map = new HashMap<>();
		
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {

			if (sheet.getRow(i).getCell(1).getStringCellValue().equals(expectedtestNAme)) {
				for (int j = i; j <= sheet.getLastRowNum(); j++) {
					map.put(sheet.getRow(j).getCell(2).getStringCellValue(),
							sheet.getRow(j).getCell(3).getStringCellValue());
					if (sheet.getRow(j).getCell(2).getStringCellValue().equals("####"))
						break;
				}
			}
			break;
		}
		return map;

	}
	
	public void writeDataIntoExcel(String expectedTestName, String status, String excelPath, String sheetName)  {
		Sheet sheet = workbook.getSheet(sheetName);
		
		for(int i=0; i<=sheet.getLastRowNum();i++) {
			if(sheet.getRow(i).getCell(1).getStringCellValue().equals(expectedTestName)) {
				Cell cell = sheet.getRow(i).createCell(4);
				cell.setCellValue(status);
				break;
			}
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(excelPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeWorkbook() {
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
