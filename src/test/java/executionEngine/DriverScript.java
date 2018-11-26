package executionEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.ActionKeywords;
import config.PathUtility;
import utilities.DriverUtil;
import utilities.Locator;
import utilities.TestdataProvider;

public class DriverScript {
	public DriverUtil drvutil;
	public WebDriver driver;
	public ActionKeywords actionKeywords;
	public String sActionKeyword;
	public Method method[];
	public PathUtility pathutil;
	public Locator locator;
	public String page_object;

	@BeforeClass
	public void classLoad(ITestContext context) {
		locator = new Locator();
		drvutil = new DriverUtil();
		driver = drvutil.DriverSetup("chrome");
		pathutil = new PathUtility(context);
		actionKeywords = new ActionKeywords(driver,locator);
		method = actionKeywords.getClass().getMethods();
	}

	@Test(dataProvider = "testSteps", dataProviderClass = TestdataProvider.class)
	public void fun(Hashtable<String, String> tdata) throws InterruptedException {
		try {
			sActionKeyword = tdata.get("Action Keyword");
			page_object= tdata.get("Page Object");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		execute_Actions();

	}

	public void execute_Actions() {

		try {
			for (int i = 0; i < method.length; i++) {

				if (method[i].getName().equals(sActionKeyword)) {
					// In case of match found, it will execute the matched method
					method[i].invoke(actionKeywords,page_object);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("execute_Actions Failed : " + e);
			e.printStackTrace();
		}
	}

}
