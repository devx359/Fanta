package TestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import executionEngine.DriverScript;
import junit.framework.Assert;

public class TC_003  extends DriverScript{
	
	public TC_003(ExtentTest testob)
	{
		test=testob;
	}
	
	@Override
	@Test(priority=2)
	public void testSteps() {
		try {
			System.out.println("Executing testcase 3");
			test.info("Executing testcase 3");
		} catch (Exception e) {
			System.out.println("TC_003: "+e);
		}
		
		//Assert.fail();
		
		
	}
}
