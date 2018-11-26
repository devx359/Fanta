package utilities;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class TestdataProvider {
	String Testcaserow;
	String TestDataRow;

	@DataProvider(name = "testSteps")
	public static Object[][] testdataProvider(ITestContext context) {

		String datasheet = context.getCurrentXmlTest().getParameter("Excelsheet");
		String sheetName = "Test Steps";
		//System.out.println("Sheet Name: "+sheetName);

		IOExcel obj = new IOExcel();

		obj.excelSetup("./src/test/java/dataEngine/" + datasheet);
		context.setAttribute("Excelobj", obj);

		Object[][] TesttabArray = null;

		try {
			TesttabArray = obj.getDataArray(sheetName);
		} catch (Exception e) {
			System.out.println("Error in Dataprovider code" + e.getMessage());
		}
		return TesttabArray;

	}

	

	@DataProvider(name = "setupdataProvider")
	public static Object[][] setupdataProvider(ITestContext context) {

		String datasheet = context.getCurrentXmlTest().getParameter("Excelsheet");
	//	String sheetName = context.getCurrentXmlTest().getParameter("sheetName");
		// System.out.println("inside test data provider");

		IOExcel obj = new IOExcel();

		obj.excelSetup("./TestData/" + datasheet);
		//context.setAttribute("Excelobj", obj);

		Object[][] TesttabArray = null;

		try {
			TesttabArray = obj.getDataArray("setup");
		} catch (Exception e) {
			System.out.println("Error in Setup Dataprovider code" + e.getMessage());
		}
		return TesttabArray;

	}
}
