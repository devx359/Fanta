package TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;
import junit.framework.Assert;
import userDefinedFunctions.Link;
import userDefinedFunctions.OtherFunctions;
import utilities.Base64EncryptionUtil;
import utilities.IOExcel;
import org.apache.commons.codec.binary.Base64;

public class Dashboard extends DriverScript {
	ExtentTest test;
	WebDriver driver;
	IOExcel xl;
	Base64EncryptionUtil b64;
	OtherFunctions otherfunc;
	String jobcode;
	Link link;

	public Dashboard(ExtentTest testob, WebDriver drivers, IOExcel xls) {
		test = testob;
		driver = drivers;
		xl = xls;
		b64 = new Base64EncryptionUtil();
		otherfunc = new OtherFunctions(drivers);
		link= new Link(driver);
	}

	@Override
	public void testSteps() {
		try {
			jobcode = xl.getStringDataBasedOnTestCaseID("Login_01", "Test Data", 4);
			System.out.println("jobcode: "+jobcode);

			System.out.println("Executing test case 1");
			otherfunc.GoogleLogin();
			otherfunc.impp_login();
			Thread.sleep(20000);
			Assert.assertTrue(otherfunc.isDisplayed("impp_text"));
			driver.get("https://itest.imerit.net/dashboard.html#/details/job_list/PC-SkgG14_bHQ/EC-HkGyEd-Hm/0");
			Assert.assertTrue(otherfunc.isDisplayed("addJob"));
			otherfunc.jobTabMoreActionClick(jobcode);
			link.JSClick("view_dashboard");
			Thread.sleep(8000);

			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
