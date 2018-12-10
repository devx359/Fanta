package TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;
import userDefinedFunctions.Link;
import userDefinedFunctions.OtherFunctions;
import utilities.Base64EncryptionUtil;
import utilities.IOExcel;

public class TC_005 extends DriverScript {
	ExtentTest test;
	WebDriver driver;
	IOExcel xl;
	Base64EncryptionUtil b64;
	OtherFunctions otherfunc;
	String jobcode;
	Link link;

	public TC_005(ITestContext context) {
		// Get all set objects from context
		test = (ExtentTest) context.getAttribute("extent");
		xl = (IOExcel) context.getAttribute("excel");
		driver = (WebDriver) context.getAttribute("driver");

		// Initiate classes
		b64 = new Base64EncryptionUtil();
		otherfunc = new OtherFunctions(driver,context);
		link = new Link(driver,context);
	}

	@Override
	@Test(priority = 2)
	public void testSteps() {
		System.out.println("Executing testcase 5");
		// SmokeTestStatus="fail";
	}

}
