package utilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

public class DriverUtil {
	public WebDriver driver = null;
	public NgWebDriver ngDriver = null;
	DesiredCapabilities cap;
	
	public WebDriver GridDriverSetup(String browser) {
		String Node = "http://10.33.91.52:4444/wd/hub";
		try {

			if (browser.equalsIgnoreCase("Chrome")) {
						
		 		cap = DesiredCapabilities.chrome();	
		 		/*cap.setBrowserName("chrome");
		 		cap.setVersion("65");
		 		cap.setPlatform(Platform.WIN10);*/
		 		//cap.acceptInsecureCerts();
		 		cap.setAcceptInsecureCerts(true);
		 		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new RemoteWebDriver(new URL(Node), cap);
			
				
				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				ngDriver.waitForAngularRequestsToFinish();
				return  driver;

			}
			if(browser.equalsIgnoreCase("firefox")) {
				cap = DesiredCapabilities.firefox();		 		
				driver = new RemoteWebDriver(new URL(Node), cap);
			
				
				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				ngDriver.waitForAngularRequestsToFinish();
				return  driver;

			}
		}
			catch (Exception e) {
				System.out.println("In DriverUtil " + e);
			}
		return  driver;
		}


	public WebDriver DriverSetup(String browser) {

		try {

			if (browser.equalsIgnoreCase("Chrome")) {
				//System.setProperty("webdriver.chrome.driver", "F:\\softwares\\selenium\\chromedriver_1.exe");
				System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver_243.exe");
				//System.setProperty("webdriver.chrome.silentOutput", "true"); //Disables chrome diagnostic logs
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.WARNING);//disables selenium logs
				driver = new ChromeDriver();
				driver.manage().window().maximize();
		 		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();


				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				ngDriver.waitForAngularRequestsToFinish();
				return driver;

			}

			else if (browser.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "F:\\softwares\\selenium\\geckodriver.exe");

				driver = new FirefoxDriver();

				/*
				 * ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				 * ngDriver.waitForAngularRequestsToFinish();
				 */
				return driver;
			}

			else {
				System.setProperty("webdriver.ie.driver", "F:\\softwares\\selenium\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				/*
				 * ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				 * ngDriver.waitForAngularRequestsToFinish();
				 */
				return driver;
			}

		} catch (Exception e) {
			System.out.println("In DriverUtil " + e);
		}
		return driver;
	}

	public WebElement findElement(By id) {
		// TODO Auto-generated method stub
		return null;
	}

	public NgWebDriver getngDriver() {
		return this.ngDriver;
	}
	public DesiredCapabilities getCapabilityObj() {
		return this.cap;
	}

}
