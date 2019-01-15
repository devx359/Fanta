package utilities;

import java.io.IOException;

import org.testng.IAttributes;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter=0;
	ExtentTest test;
	IAttributes context;
	AshotUtil ashot;
	
	
	@Override
	public boolean retry(ITestResult result) {
		RetryCountIfFailed annotation = result.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(RetryCountIfFailed.class);
		// based on the value of annotation see if test needs to be rerun
		if((annotation != null) && (counter < annotation.value()))
		{
			try {
				System.out.println("Retrying the "+result.getName()+" test case " );
				
				/*context=result.getTestContext();
			
				test=(ExtentTest) context.getAttribute("testobj");
				ashot=(AshotUtil) context.getAttribute("ashotobj");
				test.info("Retrying");
				test.addScreenCaptureFromPath(ashot.takeFullScreenShot("Error"));*/
				counter++;
				return true;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return false;
	}

}
