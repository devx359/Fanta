package TestCases;

import org.testng.annotations.Test;

import executionEngine.DriverScript;

public class TC_004 extends DriverScript{
	@Override
	@Test(priority=2)
	public void testSteps() {
		System.out.println("Executing testcase 4");
		//SmokeTestStatus="fail";
	}

}
