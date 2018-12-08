/**
 * EcommerceAuto 2017
 * 
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author debo Aug 20, 2017
 * 
 */
public class IOExcel {

	private XSSFWorkbook wbook, wbookop;
	private XSSFSheet sheet;
	private File source;
	private FileInputStream input;
	private FileOutputStream output;
	private XSSFCell cell;
	private XSSFRow crow;
	private String excelfilepath;
	public Hashtable<String, String> table;

	int rowcount = 0;

	public int Getrowcount(String sheetname) {
		try {
			sheet = wbook.getSheet(sheetname);
			rowcount = sheet.getLastRowNum();

		} catch (Exception e) {
			// Log.error("Excel file data problem: "+e);
			e.printStackTrace();
		}
		return rowcount;
	}
	
	public int GetColoumnCount(String sheetname) {
		int col_count=0;
		try {
			sheet = wbook.getSheet(sheetname);
			//rowcount = sheet.get;
			col_count=sheet.getRow(0).getLastCellNum();
			//sheet.getLastCellNum();
		


		} catch (Exception e) {
			// Log.error("Excel file data problem: "+e);
			e.printStackTrace();
		}
		return rowcount;
	}

	public void excelSetup(String filePath) {
		try {
			// System.out.println("inside excelsetup
			// @ThreadId:"+Thread.currentThread().getId());
			excelfilepath = filePath;
			input = new FileInputStream(filePath);
			wbook = new XSSFWorkbook(input);
			// sheet=wbook.getSheet(sheetname);

			/*
			 * if(wbook==null) {
			 * System.out.println("Workbook obj not created  @ThreadId:"+Thread.
			 * currentThread().getId()); } else {
			 * System.out.println("Workbook  created  @ThreadId:"+Thread.currentThread().
			 * getId()); }
			 */

		} catch (Exception e) {
			// Log.error("File Initialization constructor failed due to : "+e);
			System.out.println("excelSetup error: " + e);
		}

	}

	// *****************Get Data from Excel sheet *************//
	public Object[][] getDataArray(String SheetName)

	{

		Object[][] data = null;

		try {
			// FileInputStream Scriptfis = new FileInputStream(FilePath);

			// wbook=new XSSFWorkbook(Scriptfis);
			sheet = wbook.getSheet(SheetName);

			int Startrow = 1;
			int StartCol = 0;
			crow = sheet.getRow(0);

			int TotalRow = sheet.getLastRowNum();
			int TotalCol = crow.getLastCellNum();

			System.out.println("ExcelData TotalRow:" + TotalRow + "  TotalCol:" + TotalCol);

			data = new Object[TotalRow][1];

			for (int j = 1; j <= TotalRow; j++)

			{
				table = new Hashtable<String, String>();

				for (int k = 0; k < TotalCol; k++) {

					table.put(getCellData(0, k), getCellData(j, k));

				}

				data[j - 1][0] = table;

			}

		} catch (Exception e)

		{

			System.out.println("Could not read the Excelsheet:" + SheetName + " @ sheetpath:" + excelfilepath
					+ " @ThreadId:" + Thread.currentThread().getId() + e);
			e.printStackTrace();
		}

		return data;

	}

	// *****************Get Data from Cell of Excel sheet *************//
	public String getCellData(int RowNum, int ColNum) throws Exception {

		try {
			cell = sheet.getRow(RowNum).getCell(ColNum);

			String CellData = (String) cell.getStringCellValue();

			return CellData;
		} catch (Exception e) {
			return "";
		}

	}

	public String getExcelStringData(int row, int col, String sheetname) {
		String cellvalue = null;

		try {
			sheet = wbook.getSheet(sheetname);
			rowcount = sheet.getLastRowNum();
			cellvalue = sheet.getRow(row).getCell(col).getStringCellValue();
		} catch (Exception e) {
			// Log.error("Excel file data problem: "+e);
			System.out.println("Excel getExcelStringData problem: " + e);
			 e.printStackTrace();
		}
		return cellvalue;
	}

	//Searche excel sheet and return data based on some text(Ex Test case name)
	//Make sure testCaseID is the 1st coloumn in excel
	public String getStringDataBasedOnTestCaseID(String testCaseId, String sheetname,int DesiredDatacol) {
		String cellvalue = null;
		String return_val=null;

		try {
			sheet = wbook.getSheet(sheetname);
			rowcount = sheet.getLastRowNum();
			int i=0;
			for(i=0;i<rowcount;i++)
			{
				cellvalue = sheet.getRow(i).getCell(0).getStringCellValue();
				if(cellvalue.equalsIgnoreCase(testCaseId))
				break;
			}
			//cellvalue = sheet.getRow(i).getCell(col).getStringCellValue();
			return_val=sheet.getRow(i).getCell(DesiredDatacol).getStringCellValue();
		} catch (Exception e) {
			// Log.error("Excel file data problem: "+e);
			System.out.println("Excel getExcelStringData problem: " + e);
			 e.printStackTrace();
		}
		return return_val;
	}
	public Integer getExcelIntData(int row, int col, String sheetname) {
		Integer cellvalue = 0;

		try {
			sheet = wbook.getSheet(sheetname);
			rowcount = sheet.getLastRowNum();
			cellvalue = (int) sheet.getRow(row).getCell(col).getNumericCellValue();
		} catch (Exception e) {
			// Log.error("Excel file data problem: "+e);
			System.out.println("IOExcel getExcelIntData problem: " + e);
			// e.printStackTrace();
		}
		return cellvalue;
	}

	public void setExcelStringData(int row, int columnIndex, String stringValue, String sheetname) {

		try {
			sheet = wbook.getSheet(sheetname);
			crow = sheet.getRow(row);
			if (crow == null) {
				// Log.info("row is not Created");
				try {
					crow = sheet.createRow(row);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			cell = crow.getCell(columnIndex, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			if (cell == null) {
				crow.createCell(columnIndex).setCellValue(stringValue);
			} else {
				cell.setCellValue(stringValue);
			}

			output = new FileOutputStream(excelfilepath);
			wbook.write(output);
			output.flush();
			output.close();

		} catch (Exception e) {
			// Log.error("Excel write problem : "+e);
			e.printStackTrace();
		}

	}

	public void setExcelIntData(int rowparam, int columnIndex, int Value, String sheetname) {

		try {
			sheet = wbook.getSheet(sheetname);
			crow = sheet.getRow(rowparam);
			if (crow == null) {
				// Log.info("row is not Created");
				try {
					crow = sheet.createRow(rowparam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			cell = crow.getCell(columnIndex, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			if (cell == null) {
				crow.createCell(columnIndex).setCellValue(Value);
			} else {
				cell.setCellValue(Value);
			}

			output = new FileOutputStream(excelfilepath);
			wbook.write(output);
			output.flush();
			output.close();

		} catch (Exception e) {
			// Log.error("Excel write problem : "+e);
			e.printStackTrace();
		}

	}

	// This method is to get the Row number of the test case
	// This methods takes three arguments(Test Case name , Column Number & Sheet
	// name)
	public int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int i;
		sheet = wbook.getSheet(SheetName);
		int rowCount = Getrowcount(SheetName);
		for (i = 0; i < rowCount; i++) {
			if (getExcelStringData(i, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
				break;
			}
		}
		return i;
	}

	// This method is to get the count of the test steps of test case
	// This method takes three arguments (Sheet name, Test Case Id & Test case row
	// number)
/*	public int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		for (int i = iTestCaseStart; i <= Getrowcount(SheetName); i++) {
			if (!sTestCaseID.equals(getExcelStringData(i, Constants.Col_TestCaseID, SheetName))) {
				int number = i;
				return number;
			}
		}
		sheet = wbook.getSheet(SheetName);
		int number = sheet.getLastRowNum() + 1;
		return number;
	}*/

}
