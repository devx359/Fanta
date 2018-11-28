package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import utilities.Locator;

public class ActionKeywords {

	WebDriver driver;
	Locator locator;

	public ActionKeywords(WebDriver drivers, Locator locators) {
		driver = drivers;
		locator = locators;
	}

	public void navigate(String pageObject, String Data1) {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.get("https://www.google.com/");
	}

	public void input_text(String pageObject, String Data1) {
		try {
			By byobject = locator.getWebElement(pageObject);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(byobject)).sendKeys(Data1);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void click(String pageObject, String Data1) {

		try {

			By buttonLocator = locator.getWebElement(pageObject);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(buttonLocator)).click();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public void JSClick(String pageObject, String Data1) {
		try {	
			By LinkLocator = locator.getWebElement(pageObject);
			
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement elementToClick=wait.until(ExpectedConditions.visibilityOfElementLocated(LinkLocator));
			//System.out.println(strxpath+elementToClick.isDisplayed());
			//System.out.println(strxpath+elementToClick.isEnabled());
		
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			//System.out.println("Clicked on "+strxpath);
	
		} catch (Exception e) {
			System.out.println("Unable to click on link " + pageObject +e);
			e.printStackTrace();
			Assert.fail();
			
		}

	}

	public void clickonPresent(String pageObject, String Data1) {
		try {
			By LinkLocator = locator.getWebElement(pageObject);
			WebDriverWait wait = new WebDriverWait(driver, 10);

			wait.until(ExpectedConditions.presenceOfElementLocated(LinkLocator)).click();
		} catch (Exception e) {
			System.out.println("Unable to click on link: " + pageObject + " " + e);

			Assert.fail();
		}

	}

	public void isEnable(String pageObject, String Data1) {

		try {

			By buttonLocator = locator.getWebElement(pageObject);
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator)).isEnabled();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void waitFor(String pageObject, String Data1) throws Exception {
		Thread.sleep(Integer.parseInt(Data1));
	}

	public void press_enter(String pageObject, String Data1) {
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.ENTER);
	}

	public void closeBrowser(String Object) {
		driver.quit();
	}

}
