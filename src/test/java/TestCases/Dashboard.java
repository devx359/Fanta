package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;

import executionEngine.DriverScript;
import junit.framework.Assert;
import userDefinedFunctions.Link;
import userDefinedFunctions.OtherFunctions;
import utilities.AshotUtil;
import utilities.Base64EncryptionUtil;
import utilities.IOExcel;
import utilities.RetryCountIfFailed;
import utilities.RetryAnalyzer;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

public class Dashboard extends DriverScript {
	ExtentTest test;
	WebDriver driver;
	IOExcel xl;
	Base64EncryptionUtil b64;
	OtherFunctions otherfunc;
	String jobcode;
	Link link;
	AshotUtil ashot;

	public Dashboard(ITestContext context) {
		// Get all set objects from context
		test = (ExtentTest) context.getAttribute("extent");
		xl = (IOExcel) context.getAttribute("excel");
		driver = (WebDriver) context.getAttribute("driver");

		// Initiate classes
		b64 = new Base64EncryptionUtil();
		otherfunc = new OtherFunctions(driver, context);
		link = new Link(driver, context);
		ashot = new AshotUtil(driver);
	}

	
	@Test
	public void testSteps() {
		try {
		/*	jobcode = xl.getStringDataBasedOnTestCaseID("Login_01", "Test Data", 4);
			System.out.println("jobcode: " + jobcode);
			test.info("inside dashboard");

			System.out.println("Executing test case 1");
			otherfunc.GoogleLogin();
			otherfunc.impp_login();
			Thread.sleep(20000);
			Assert.assertTrue(otherfunc.isDisplayed("impp_text"));
			driver.get("https://itest.imerit.net/dashboard.html#/details/job_list/PC-SkgG14_bHQ/EC-HkGyEd-Hm/0");
			Assert.assertTrue(otherfunc.isDisplayed("addJob"));
			otherfunc.jobTabMoreActionClick(jobcode);
			link.JSClick("view_dashboard");
			otherfunc.openNewTabandSwitchToIt(driver);*/
			
			/*driver.get("https://www.google.com");
			Thread.sleep(2000);*/
			Assert.fail();
			/*ashot.embedScreenshotExtent(test, "dashboard page", "scr2"); // This will embed screenshot in extent report
*/		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.error(e);
		}
		
		

	}
	

}
