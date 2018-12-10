package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import executionEngine.DriverScript;
import junit.framework.Assert;
import userDefinedFunctions.Link;
import userDefinedFunctions.OtherFunctions;
import utilities.AshotUtil;
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
	AshotUtil ashot;

	public Dashboard(ITestContext context)
	{
		//Get all set objects from context
		test=(ExtentTest)context.getAttribute("extent");
		xl=(IOExcel)context.getAttribute("excel");
		driver=(WebDriver) context.getAttribute("driver");
		
		//Initiate classes
		b64 = new Base64EncryptionUtil();
		otherfunc = new OtherFunctions(driver,context);
		link= new Link(driver,context);
		ashot = new AshotUtil(driver);
	}

	@Override
	public void testSteps() {		
		try {		
			jobcode = xl.getStringDataBasedOnTestCaseID("Login_01", "Test Data", 4);
			System.out.println("jobcode: "+jobcode);
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
			/*String path=ashot.takeFullScreenShot("scr").replace(".\\", System.getProperty("user.dir").toString()+"\\");
			System.out.println(path);*/
			String pth=ashot.takeFullScreenShot("scr");
			System.out.println("Screen shot path "+pth);
			test.info("test", MediaEntityBuilder.createScreenCaptureFromPath(pth).build());
			//System.out.println(System.getProperty("user.dir"));	 
			//throw new ArithmeticException("Student is not eligible for registration"); 
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.error(e);
		}

	}

}
