package TestCases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.apache.commons.codec.digest.DigestUtils;


public class Demo {
	WebDriver driver;

	@Test
	public void fun() throws AWTException, InterruptedException {
		String val = "chrome";

		/*switch (val) {
		
		/*JSONObject jsonreq = new JSONObject();
			jsonreq.put("acl", "Manager");
			jsonreq.put("identity", "I2034");
			jsonreq.put("project_code", "LLA_001");
			jsonreq.put("sub_project_code", "LLA_001_1");
			jsonreq.put("role", "OTHER");*/
	//	String data="{\"identity\":\""+data+"\",\"acl\":\"1st Operator\",\"project_code\":\"LLA_001\",\"sub_project_code\":\"LLA_001_1\",\"role\":\"OTHER\"}";
			
		case "chrome":
			System.out.println("its chrome");
			break;
		case "ie":
			System.out.println("its ie");
			break;
		}*/
		
		/*int i=1;
		do {
			if(i==1)
			{
				System.out.println("Hey its 1");
				i++;
			}
		}while(i==1);*/
		
		/*for(int j=0;j<5;j++) {
		for(int i=0;i<5;i++)
		{
			System.out.println("i = "+i);
			if(i==3) {
				continue;
			}
			System.out.println("after break ,inside for");
		}
		
		System.out.println("Aftre inside for ");
		}
		System.out.println("After for");*/
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_CONTEXT_MENU);
		
		
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver_243.exe");
		driver= new ChromeDriver();
		
		//driver.get("https://www.google.com/");
	//	driver.navigate().to("https://www.youtube.com/");
		driver.get("https://www.youtube.com/");
		WebDriverWait wait= new WebDriverWait(driver,10);
		ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='click-target']")));
	
		//WebElement ele = driver.findElement(By.xpath("//a[@id='click-target']"));
		Thread.sleep(5000);
		//Pagefactory.
		
	}
	
	@FindBy(id="gh")
	private WebElement ele;
	
	@FindBy(id="gh1")
	private WebElement ele1;
	
	
	
	@AfterTest
	public void shurdown()
	{
		driver.quit();
	}

}
