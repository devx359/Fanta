package config;

import org.testng.ITestContext;

import com.jayway.restassured.RestAssured;

public class PathUtility {
	public static String BaseFilepath;
	public static String RequestAPIUrl;
	public static String APIurl;
	public static String RethinkUrl;
	public static String RethinkPassword;
	public static String RethinkDB;
	public static int RethinkPort;
	public static String task_img_path;
	public static String mysqlhostname;
	public static String mysqlport;
	public static String mysqlusername;
	public static String mysqlpassword;
	public static String mysqldbname;
	public static String screenshotFolder;
	public static String OR_File;
	public String weburl;
	public static String psubmitfile;
	public static String simple_OP_tool_url;
	public static String simple_QC_tool_url;
	public static String op_metadata_json_file_path;
	public static String qc_metadata_json_file_path;
	public static String impp_home;
	public static String firebaseProjectID,FB_SubmitAPI;


	public PathUtility(ITestContext context) {
		String environment =context.getCurrentXmlTest().getParameter("environment");
		
		OR_File=".\\src\\test\\java\\config\\OR_E1.properties";
		simple_OP_tool_url="https://testtools.imerit.net/lightning_v1";
		simple_QC_tool_url="https://testtools.imerit.net/lightning_qc_v1";
		op_metadata_json_file_path="C:\\Users\\Indrajit-PC\\Desktop\\Jmeter\\Load Test\\Project1\\meta_data_op.json";
		qc_metadata_json_file_path="C:\\Users\\Indrajit-PC\\Desktop\\Jmeter\\Load Test\\Project1\\meta_data_qc.json";
		impp_home="https://itest.imerit.net/dashboard.html#/welcome";
		firebaseProjectID="platform-203105";
		screenshotFolder = ".\\Screenshots\\";
		if (environment.equalsIgnoreCase("itest")) {
			
			BaseFilepath = "D:\\Reports";
			RequestAPIUrl = "https://itestapi.imerit.net:9888";
			APIurl = "https://itestapi.imerit.net:32845";
			RethinkUrl = "54.202.116.149";
			//RethinkUrl = "52.38.123.44";
			RethinkPassword = "passW0rd#123";
			RethinkDB = "impp_tasking_dev";
			RethinkPort = 28015;
			task_img_path = "https://beta.imerit.net/tasking/uploads/images/070617_street3_gray/gray_frame_ts_0016500.png";
			psubmitfile="https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/CTRCONTRACTS005921-5.jpeg";
			mysqlhostname = "testimppinstance.cy4e3hacstmv.us-west-2.rds.amazonaws.com";
			mysqlport = "3306";
			mysqlusername = "imppdb";
			mysqlpassword = "rds@ws#2017";
			mysqldbname = "impp_poc";
			weburl="https://itest.imerit.net/";  //Web url
			
		} else if (environment.equalsIgnoreCase("alpha")) {
			
			BaseFilepath = "D:\\Reports";
			RequestAPIUrl = "https://itestapi.imerit.net:9889";
			APIurl = "https://itestapi.imerit.net:32846";
			RethinkUrl = "35.165.23.169";
			RethinkPassword = "passW0rd#123";
			RethinkDB = "impp_alpha";
			RethinkPort = 28015;
			task_img_path = "https://beta.imerit.net/tasking/uploads/images/070617_street3_gray/gray_frame_ts_0016500.png";
			psubmitfile="https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/CTRCONTRACTS005921-5.jpeg";
			mysqlhostname = "testimppinstance.cy4e3hacstmv.us-west-2.rds.amazonaws.com";
			mysqlport = "3306";
			mysqlusername = "imppdb";
			mysqlpassword = "rds@ws#2017";
			mysqldbname = "impp_alpha_09082018";
			weburl="https://alpha.imerit.net/";  //Web url
			FB_SubmitAPI="us-central1-platform-203105.cloudfunctions.net";


		}
		
	//	OR_Login=".\\src\\test\\java\\ObjectRepository\\OR_E1.properties";
	}

}
