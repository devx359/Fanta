package TestCases;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;
import userDefinedFunctions.Link;
import userDefinedFunctions.OtherFunctions;
import utilities.Base64EncryptionUtil;
import utilities.IOExcel;

public class WiniumDemo extends DriverScript {

	ExtentTest test;
	WebDriver driver;
	IOExcel xl;
	Base64EncryptionUtil b64;
	OtherFunctions otherfunc;
	String jobcode;
	Link link;

	public WiniumDemo(ITestContext context) {
		// Get all set objects from context
		test = (ExtentTest) context.getAttribute("extent");
		xl = (IOExcel) context.getAttribute("excel");
		driver = (WebDriver) context.getAttribute("driver");

		// Initiate classes
		b64 = new Base64EncryptionUtil();
		otherfunc = new OtherFunctions(driver, context);
		link = new Link(driver, context);
	}

	@Test(priority = 2)
	public void testSteps() {

		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath("C:\\WINDOWS\\system32\\notepad.exe");
		try {
			WiniumDriver driver2 = new WiniumDriver(new URL("http://localhost:9999"), options);
			driver2.findElementByClassName("Edit").sendKeys("This is sample test");
			driver2.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
