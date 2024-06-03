package Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readexcel {
		public static Object[][] readData(String dataSheet) throws IOException {
			File excelFile  = new File("./src/test/resources/data/"+dataSheet+".xlsx");
			System.out.println(excelFile);
			System.out.println(excelFile.exists());
			FileInputStream File = new FileInputStream(excelFile);
	        XSSFWorkbook workbook = new XSSFWorkbook(File);
			XSSFSheet sheet = workbook.getSheet("Registration");
			int rowNum = sheet.getLastRowNum();
			int cellNum = sheet.getRow(0).getLastCellNum();
			System.out.println("row number = "+ rowNum );
			System.out.println("cell number = "+ cellNum);
			Object[][] value = new Object[rowNum][cellNum];
			
			for(int i=1; i<rowNum+1; i++)
			{
			XSSFRow row1 = sheet.getRow(i);
				for(int j=0; j<cellNum; j++)
				{
					XSSFCell cellValue = row1.getCell(j);//.getStringCellValue();
					DataFormatter dataFormatter = new DataFormatter();
					String formattedCellStr = dataFormatter.formatCellValue(cellValue);
					 value[i-1][j]= formattedCellStr;
					 System.out.println(formattedCellStr);
					
				}
			}
			workbook.close();
			return value;
		
			}
		

	}
