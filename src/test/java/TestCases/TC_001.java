package TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;
import utilities.Base64EncryptionUtil;
import utilities.IOExcel;
import org.apache.commons.codec.binary.Base64;
public class TC_001 extends DriverScript {
	ExtentTest test;
	WebDriver driver;
	IOExcel xl;
	Base64EncryptionUtil b64;
	public TC_001(ExtentTest testob,WebDriver drivers,IOExcel xls)
	{
		 test = testob;
		 driver = drivers;
		 xl = xls;
		 b64 = new Base64EncryptionUtil();
	}

	@Override
	public void testSteps() {
		
		System.out.println("Executing test case 1");
		test.info("Executing test case 1");
		driver.get("https://www.google.com");
		System.out.println("End Executing test case 1");
		String data=xl.getStringDataBasedOnKey("Login_01", "Test Data", 0);
		System.out.println("getlast col "+xl.GetColoumnCount("Test Data"));
		System.out.println("After encoding "+b64.encode("TC_001"));
		System.out.println("After decoding "+b64.decode("VENfMDAx"));
	
		
	}



}
