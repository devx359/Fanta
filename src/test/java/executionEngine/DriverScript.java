package executionEngine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.ExtentManager;
import config.ActionKeywords;
import config.PathUtility;
import projSpecificUtilities.Dbconnection;
import utilities.DriverUtil;
import utilities.IOExcel;
import utilities.Locator;
import utilities.TestdataProvider;
import utilities.ExtentManager;;

/*To Run this from Maven
 * mvn clean install -DsuiteXmlFile=testRunner2.xml -DnumberOfTests=1 -DjobNumber=3
 * 
*/
public class DriverScript {
	public DriverUtil drvutil;
	public WebDriver driver;
	public ActionKeywords actionKeywords;
	public String sActionKeyword;
	public Method method[];
	public PathUtility pathutil;
	public Locator locator;
	public String page_object;
	public IOExcel excel;
	public String tc_id;
	public String runmode;
	public int total_testCases;
	public String action;
	public int testSteps;
	public String testClass;
	public static String SmokeTestStatus = "pass";
	public String sheetName;
	int numberOfTests; // how many parallel tests you want to run
	int jobNumber; // job serial number
	ExtentManager ExtentManagerObj;
	ExtentReports reports;
	public ExtentTest test;
	Dbconnection dbcon;
	IOExcel xl;
	ITestContext contextob;

	@BeforeTest
	public void classLoad(ITestContext context) {
		contextob = context;// Set the context object which we will later pass it to reflection invoked
							// testclass method .This will
		// contain driver,excel obj
		locator = new Locator();
		drvutil = new DriverUtil();
		driver = drvutil.DriverSetup("chrome");
		context.setAttribute("driver", driver);
		pathutil = new PathUtility(context);
		actionKeywords = new ActionKeywords(driver, locator);
		method = actionKeywords.getClass().getMethods();
		sheetName = context.getCurrentXmlTest().getParameter("sheetName");
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent(this.getClass().getSimpleName().toString());
		System.out.println("class load");
	}

	/*
	 * @Test(dataProvider = "testSteps", dataProviderClass = TestdataProvider.class,
	 * priority = 1) public void testRunner(Hashtable<String, String> tdata,
	 * ITestContext context) throws InterruptedException {
	 * 
	 * try {
	 * 
	 * excel = (IOExcel) context.getAttribute("Excelobj");// Get excel object
	 * already initialized by testdata
	 * 
	 * tc_id = tdata.get("Test Case ID"); runmode = tdata.get("Runmode"); testClass
	 * = tdata.get("TestClass"); System.out.println("TestClass: " + testClass +
	 * " |runmode: " + runmode + " |tc_id: " +
	 * tc_id+" |SmokeTestStatus: "+SmokeTestStatus); execute_testcase2(); } catch
	 * (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 */

	@Test(priority = 1)
	public void testRunner(ITestContext context) throws InterruptedException {

		try {

			System.out.println("Inside testrunner");
			/*
			 * numberOfTests = 1; jobNumber = 1;
			 */

			numberOfTests = Integer.parseInt(System.getProperty("numberOfTests"));
			jobNumber = Integer.parseInt(System.getProperty("jobNumber"));

			int startRow = (numberOfTests * (jobNumber - 1)) + 1;
			System.out.println("startRow:" + startRow);
			String datasheet = context.getCurrentXmlTest().getParameter("Excelsheet");
			String sheetName = context.getCurrentXmlTest().getParameter("sheetName");

			System.out.println("----------------------------------------------------------");
			System.out.println("                   " + sheetName + "                          ");
			System.out.println("----------------------------------------------------------");

			xl = new IOExcel();
			xl.excelSetup("./src/test/java/dataEngine/" + datasheet);
			context.setAttribute("excel", xl);

			for (int i = startRow; i < (startRow + numberOfTests); i++) {

				tc_id = xl.getExcelStringData(i, 0, sheetName);
				runmode = xl.getExcelStringData(i, 2, sheetName);
				testClass = xl.getExcelStringData(i, 4, sheetName);

				System.out.println("----------X-----------" + tc_id + "-------------X-----------");
				test = reports.createTest(tc_id);
				context.setAttribute("extent", test);
				ExtentManagerObj.setReportName(sheetName);
				test.info("running " + tc_id);
				System.out.println(i + "TestClass: " + testClass + " |runmode: " + runmode + " |tc_id: " + tc_id
						+ " |SmokeTestStatus: " + SmokeTestStatus);
				execute_testcase2();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// @Test(priority = 2)
	public void testSteps() {
	};

	// Invokes reflection classes to instantiate test classes and call the test
	// methods
	public void execute_testcase2() {
		try {
			if (runmode.equalsIgnoreCase("Yes")) {
				if ((!SmokeTestStatus.equalsIgnoreCase("fail"))) {
					Class<?> clazz = Class.forName("TestCases." + testClass); // Gets the class object from class name
					/*
					 * Constructor<?> cons = clazz.getConstructor(ExtentTest.class, WebDriver.class,
					 * IOExcel.class); Object obj = cons.newInstance(test, driver, xl);
					 */
					/*
					 * Constructor<?> cons = clazz.getConstructor(); Object obj =
					 * cons.newInstance();
					 */
					Constructor<?> cons = clazz.getConstructor(ITestContext.class);
					Object obj = cons.newInstance(contextob);
					((DriverScript) obj).testSteps();
				}
			}
		} catch (Exception e) {
			System.out.println("issue in excute testcase " + e);
			e.printStackTrace();
		}
	}

	public void execute_testcase() {
		try {
			if (runmode.equalsIgnoreCase("Yes")) {

				testSteps = excel.Getrowcount("Test Steps");
				System.out.println("test steps " + testSteps);

				for (int i = 1; i <= (testSteps); i++) {

					if (excel.getExcelStringData(i, 0, "Test Steps").equalsIgnoreCase(tc_id)) {

						sActionKeyword = excel.getExcelStringData(i, 5, "Test Steps");
						page_object = excel.getExcelStringData(i, 4, "Test Steps");
						System.out.println(excel.getExcelStringData(i, 0, "Test Steps") + " Step: "
								+ excel.getExcelStringData(i, 1, "Test Steps") + " action: " + sActionKeyword
								+ " page_object: " + page_object);
						execute_Actions();
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void execute_Actions() {

		try {
			for (int i = 0; i < method.length; i++) {

				if (method[i].getName().equals(sActionKeyword)) {
					// In case of match found, it will execute the matched method
					method[i].invoke(actionKeywords, page_object);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("execute_Actions Failed : " + e);
			e.printStackTrace();
		}
	}

	@AfterTest
	public void shutDown() {
		try {
			reports.flush();
			String Jname = System.getProperty("JenkinsJobName"); // Fetches job name from maven commandline in jenkins
			ExtentManagerObj.copyFile("C:\\jenkins\\workspace\\" + Jname + "\\");
			driver.quit();
		} catch (Exception e) {
			System.out.println("Report copy issue to jenkins workspace " + e);
			e.printStackTrace();
		}
	}
}
