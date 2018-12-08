package userDefinedFunctions;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import utilities.Locator;


public class TextBox {

	WebDriver driver;
	Locator locator;

	public TextBox(WebDriver driver) {
		this.driver = driver;
		locator= new Locator();		
	}

	public void SetText(String strxpath, String strData) {

		try {
			By textboxLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textboxLocator)).sendKeys(strData);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	public void enterKeyAfterSendKeys(String strxpath) {

		try {
			By textboxLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(textboxLocator)).sendKeys(Keys.ENTER);
			System.out.println("enter key pressed");
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void getText(String strxpath, String strData) {
		try {
			
			By textboxLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(textboxLocator)).getText();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void newWindowSetText(String strxpath, String strData) {

		try {
			By WindowLocator = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(WindowLocator)).click();
			String parentWindow = driver.getWindowHandle();
			System.out.println("Parent Window ID is : " + parentWindow);

			Set<String> allWindow = driver.getWindowHandles();

			int count = allWindow.size();
			System.out.println("Total Window : " + count);

			for (String child : allWindow) {
				if (!parentWindow.equalsIgnoreCase(child)) {
					driver.switchTo().window(child);
					driver.manage().window().maximize();
					WebDriverWait wait2 = new WebDriverWait(driver, 20);
					wait2.until(ExpectedConditions.elementToBeClickable(WindowLocator)).sendKeys(strData);
				}
			}
			driver.switchTo().window(parentWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
