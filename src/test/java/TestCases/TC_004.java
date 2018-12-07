package TestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;

public class TC_004 extends DriverScript{
	
	public TC_004(ExtentTest testob)
	{
		test=testob;
	}
	@Override
	@Test(priority=2)
	public void testSteps() {
		try {
			System.out.println("Executing testcase 4");
			test.info("Executing testcase 4");
		} catch (Exception e) {
			System.out.println("TC_004: "+e);
		}
		
		//SmokeTestStatus="fail";
	}

}
