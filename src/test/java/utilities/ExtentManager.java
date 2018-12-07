package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
 
public class ExtentManager {
	
	private  ExtentReports extent;
	private  ExtentTest test;
	private  ExtentHtmlReporter htmlReporter;
	private  static String filePath ;
	public static String filepath;
	
	
	public  ExtentReports GetExtent(String ReportName){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String filename=ReportName.replace(" ","").replace(":","").replace("/","").replace("//", "").replace(".","");
		//System.out.println(filename);
		
		 filePath="./Reports/"+filename+"_"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".html";
		// filePath="./src/ExtentReports/Extent_Report"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".html";
		 filepath=filename+".html";
		 if (extent != null)
        return extent; //avoid creating new instance of html file
		
        extent = new ExtentReports();		
		extent.attachReporter(getHtmlReporter(ReportName));
		extent.setSystemInfo("OS", "Windows 10");
        extent.setSystemInfo("Environment", "ITest");
        extent.setSystemInfo("User Name", "Debapriyo Haldar");
		return extent;
	}
	
	public void setReportName(String reportName)
	{
        htmlReporter.config().setReportName(reportName);
	}
	
	//This is the tab name
	public void setReportTitle(String reportName)
	{
        htmlReporter.config().setDocumentTitle(reportName);
   
	}
 
	private  ExtentHtmlReporter getHtmlReporter(String ReportName) {
		System.out.println(filePath);
	
        htmlReporter = new ExtentHtmlReporter(filePath);
		
	// make the charts visible on report open
     //   htmlReporter.config().setChartVisibilityOnOpen(true);		
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName(ReportName);
        return htmlReporter;
	}
	
	/*public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}*/
	
	//Copies File to Jenkins Workspace folder for email purposes
		public static void copyFile(String JenkinsWorkspacePath)
		{
			try {
				String Jenkinsfilepath=JenkinsWorkspacePath+filepath;
				File source= new File(filePath);
				File destfile = new File(Jenkinsfilepath);
				FileUtils.copyFile(source, destfile);
				System.out.println("Report Copied to Jenkins Workspace");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Report copy issue to Jenkins workspace");
				e.printStackTrace();
			}
			
		}
}
