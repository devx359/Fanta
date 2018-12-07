package TestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;

public class TC_002 extends DriverScript{
	public TC_002(ExtentTest testob)
	{
		test=testob;
	}
	@Override
	@Test(priority=2)
	public void testSteps() {
		try {
			System.out.println("Executing testcase 2");
			test.info("Executing testcase 2");
			System.out.println("End Executing test case 2");
		} catch (Exception e) {
			System.out.println("TC_002: "+e);
		}
		
	}
	

}
