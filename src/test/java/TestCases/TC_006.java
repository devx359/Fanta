package TestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;

public class TC_006 extends DriverScript{
	public TC_006(ExtentTest testob)
	{
		test=testob;
	}
	@Override
	@Test(priority=2)
	public void testSteps() {
		System.out.println("Executing testcase 6");
		//SmokeTestStatus="fail";
	}

}
