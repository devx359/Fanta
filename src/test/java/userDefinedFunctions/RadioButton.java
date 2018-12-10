package userDefinedFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentTest;

import utilities.Locator;

public class RadioButton {
	WebDriver driver;
	Locator locator;
	ExtentTest test;
	
	public RadioButton(WebDriver drivers,ITestContext context) {
		driver = drivers;
		locator = new Locator();
		test = (ExtentTest)context.getAttribute("extent");
	}

	
}
