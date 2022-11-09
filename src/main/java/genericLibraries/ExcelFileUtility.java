package genericLibraries;

import java.io.FileInputStream;
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
	
	public void excelFileInitialization(String excelPath) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(excelPath);
		workbook = WorkbookFactory.create(fis);
	}

	public Map<String, String> fetchDataFromExcel(String expectedtestNAme) throws EncryptedDocumentException, IOException {
		
		Sheet sheet = workbook.getSheet("TestData");
		Map<String, String> map = new HashMap<String, String>();

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
	
	public void writeDataIntoExcel(String expectedTestName, String status, String excelPath) throws IOException {
		Sheet sheet = workbook.getSheet("TestData");
		
		for(int i=0; i<=sheet.getLastRowNum();i++) {
			if(sheet.getRow(i).getCell(1).getStringCellValue().equals(expectedTestName)) {
				Cell cell = sheet.getRow(i).createCell(4);
				cell.setCellValue(status);
				break;
			}
		}
		
		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
	}
	
	public void closeWorkbook() throws IOException {
		workbook.close();
	}

}
