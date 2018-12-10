package userDefinedFunctions;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentTest;
import com.paulhammant.ngwebdriver.NgWebDriver;

import utilities.Locator;

public class Button {

	WebDriver driver;
	ExtentTest test;
	Locator locator;

	public Button(WebDriver drivers,ITestContext context) {
		driver = drivers;
	
		locator = new Locator();
		test = (ExtentTest)context.getAttribute("extent");
	}

	// WebDriverSelector webdriver = new WebDriverSelector();

	public void Click(String strxpath) {

		try {
			By buttonLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(buttonLocator)).click();

		} catch (Exception e) {
			e.printStackTrace();
			test.error(e);
		}

	}

	// Uses JavaScript to click button

	public void jsClick(String strxpath, String filepath) {

		try {
			By LinkLocator = locator.getWebElement(strxpath);
			WebElement elementToClick = driver.findElement(LinkLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.sendKeys(filepath);

		} catch (Exception e) {
			e.printStackTrace();
			test.error(e);
		}

	}

	// Checks if the button is Enabled

	public Boolean isEnabled(String strxpath) {

		Boolean enabled = false;
		try {

			By buttonLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 50);
			enabled = wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator)).isEnabled();
			return enabled;

		} catch (Exception e) {
			e.printStackTrace();
			test.error(e);
			return enabled;
		}

	}

}
