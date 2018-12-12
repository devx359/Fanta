package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;

import config.PathUtility;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class AshotUtil {
	WebDriver driver;
	public AshotUtil(WebDriver drivers)
	{
		driver=drivers;
	}

	// Full Screen Screenshots

	public String takeFullScreenShot( String filename) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// String
		// filepath="./Screenshot/Test/"+filename+"_"+timestamp.toString().replace(":",
		// "_").replace("-","_").replace(" ","_").replace(".", "_")+".png";
		String filepath = PathUtility.screenshotFolder + filename + "_"
				+ timestamp.toString().replace(":", "_").replace("-", "_").replace(" ", "_").replace(".", "_") + ".png";

		try {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(driver);
			ImageIO.write(screenshot.getImage(), "PNG", new File(filepath));

		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Screenshot Taken..Cheeeese !!"+filepath);
		//String path1=filepath.replace("/", "\\\\");
		return filepath.replace("/", "\\\\").replace(".\\", System.getProperty("user.dir").toString()+"\\");

	}
	//Scrollable + element only screenshot +doesnt use Jquery internally
	public String takeScreenShotofElement(String filename, WebElement element) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// String
		// filepath="./Screenshot/Test/"+filename+"_"+timestamp.toString().replace(":",
		// "_").replace("-","_").replace(" ","_").replace(".", "_")+".png";
		String filepath = PathUtility.screenshotFolder + filename + "_"
				+ timestamp.toString().replace(":", "_").replace("-", "_").replace(" ", "_").replace(".", "_") + ".png";

		try {
			Screenshot screenshot = new AShot()
					.coordsProvider(new WebDriverCoordsProvider())
					.shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(driver,element);
			ImageIO.write(screenshot.getImage(), "PNG", new File(filepath));

		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Screenshot Taken..Cheeeese !!");
		return filepath.replace("/", "\\\\");

	}
	//Jquery verison of element only screenshot ..use it if the above one fails
	public String takeScreenShotofElementUsingJquery( String filename, WebElement element) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		String filepath = PathUtility.screenshotFolder + filename + "_"
				+ timestamp.toString().replace(":", "_").replace("-", "_").replace(" ", "_").replace(".", "_") + ".png";

		try {
			Screenshot screenshot = new AShot()					
					.shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(driver,element);
			ImageIO.write(screenshot.getImage(), "PNG", new File(filepath));

		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Screenshot Taken..Cheeeese !!");
		return filepath.replace("/", "\\\\");

	}


	
	
	/* For taking normal limited area (viewport only )fast screenshots */
	public String takeScreenShot( String filename) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String filepath = PathUtility.screenshotFolder + filename + "_"
				+ timestamp.toString().replace(":", "_").replace("-", "_").replace(" ", "_").replace(".", "_") + ".png";
		// System.out.println("screenshot: "+filepath);
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File destfile = new File(filepath);
			FileUtils.copyFile(source, destfile);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return filepath.replace("/", "\\\\");
	}
	
	/*embeds screenshot in extent report*/
	public void embedScreenshotExtent(ExtentTest extentTestObject,String description,String screenshotName)
	{
		try {
			ExtentTest test=extentTestObject;
			MediaEntityModelProvider med;
			test.info(description, MediaEntityBuilder.createScreenCaptureFromPath(takeFullScreenShot(screenshotName)).build());
		} catch (IOException e) {
			
			System.out.println("Error printing screenshot "+e);
		///	e.printStackTrace();
		}
		
		
		
	}

}
