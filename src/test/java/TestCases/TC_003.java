package TestCases;

import org.testng.annotations.Test;

import executionEngine.DriverScript;
import junit.framework.Assert;

public class TC_003  extends DriverScript{
	@Override
	@Test(priority=2)
	public void testSteps() {
		System.out.println("Executing testcase 3");
		
		Assert.fail();
		
		
	}
}
