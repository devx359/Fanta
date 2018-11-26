package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import utilities.Locator;



public class ActionKeywords {

	WebDriver driver;
	Locator locator;

	public ActionKeywords(WebDriver drivers,Locator locators) {
		driver = drivers;
		locator=locators;
	}

	public void navigate(String Object) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.com/");
	}

	public void input(String Object) {
		try {
			By byobject = locator.getWebElement(Object);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(byobject)).sendKeys("mango");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}



	public void waitFor(String Object) throws Exception {
		Thread.sleep(5000);
	}

	public void press_enter(String Object) {
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.ENTER);
	}

	public void closeBrowser(String Object) {
		driver.quit();
	}

}
