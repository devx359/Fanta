package userDefinedFunctions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import utilities.Locator;

/*
generic functions should be added here
Author:Debapriyo 13-oct-2018
*/
public class OtherFunctions {
	WebDriver driver;

	Locator locator;

	public OtherFunctions(WebDriver drivers) {
		driver = drivers;
		locator = new Locator();
	}

	public void GoogleLogin() {

		try {
			driver.get("https://www.google.com");

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.ignoring(ElementNotVisibleException.class);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='gb_70']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("identifier")))
					.sendKeys("debapriyo.halder@imerit.net");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Next')]"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("imerit2359");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Next')]"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text']")))
					.sendKeys("sound check");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	public void impp_login() {
		try {
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.get("https://itest.imerit.net/");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			WebElement elementToClick;
			WebDriverWait wait = new WebDriverWait(driver, 30);
			elementToClick = wait.until(
					ExpectedConditions.visibilityOfElementLocated(locator.getWebElement("button_connect_with_google")));
			executor.executeScript("arguments[0].click();", elementToClick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DirectXpathJSClick(String strxpath) {
		try {
			WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.click();
		} catch (Exception e) {
			System.out.println("Unable to click on link" + strxpath + e);
			// Assert.fail();
		}

	}

	// Helpfull with fixed xpaths + dyanmic element after that
	public void XpathDataClick(String strxpath1, String dynamicdata) {
		try {
			String strXpath1 = locator.getlocator(strxpath1).trim();
			String locatorValue = strXpath1.split(";")[1];

			String strxpath = locatorValue + dynamicdata + "')]";
			WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
		} catch (Exception e) {
			System.out.println("Unable to click on link" + strxpath1);
			// Assert.fail();
		}

	}

	/*
	 * Dynamic xpath Useful only when you need to pass 1 dynamic data inside a xpath
	 * Ex: xpath1 +'pass dynamic value'+xpath2
	 */
	public void xpathdynamicdataClick(String strxpaths1, String strxpaths2, String dynamicdata) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);

			String strXpath1 = locator.getlocator(strxpaths1).trim();
			String locatorValue = strXpath1.split(";")[1];
			String strXpath2 = locator.getlocator(strxpaths2).trim();
			String locatorValue2 = strXpath2.split(";")[1];
			String strxpath = locatorValue + dynamicdata + locatorValue2;
			// System.out.println(strxpath);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(strxpath))).click();
			/*
			 * WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			 * 
			 * elementToClick.click();
			 */
		} catch (Exception e) {
			System.out.println("Unable to click on link: " + strxpaths1 + strxpaths2 + " " + e);
			Assert.fail();
		}

	}

	public void jobTabMoreActionClick(String jobCode) {
		try {
			JavascriptExecutor jx= (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//span[contains(text(),'" + jobCode + "')]/../following-sibling::div[6]/a")));
			jx.executeScript("arguments[0].click();", ele);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String getText(String strxpath) {
		String res = null;
		try {

			By Locators = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			res = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators)).getText();
			return res;

		} catch (Exception e) {
			e.printStackTrace();
			return res;

		}

	}

	public Boolean isDisplayed(String strxpath) {
		Boolean result = false;

		try {

			By Locators = locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			result = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators)).isDisplayed();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			return result;

		}

	}

	public void alertAccept() {
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 20);
		 * wait.until(ExpectedConditions.alertIsPresent());
		 */
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();

			System.out.println("Clicked OK");
		} catch (Exception e) {
			System.out.println("Failed to switch to Alert: " + e);
			e.printStackTrace();
		}

	}

	public void alertDismiss() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().dismiss();
			System.out.println("Clicked cancel");
		} catch (Exception e) {
			System.out.println("Failed to switch to Alert: " + e);
			e.printStackTrace();
		}

	}

	public void switchFrame(String strXpath) {
		try {
			By buttonLocator = locator.getWebElement(strXpath);
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator));
			WebElement frame = driver.findElement(buttonLocator);
			driver.switchTo().frame(frame);
			// driver.switchTo().frame(0);
			System.out.println("Frame switched  ");
		} catch (Exception e) {
			System.out.println("Failed to switch other Frame " + e);
			e.printStackTrace();
			Assert.fail();

		}

	}
}
