package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class CreateTestNGFiles {
	IOExcel exc;
	ArrayList totalPartitions;
	int minPartition;
	int maxPartition;
	int currentpartition;
	int noofrows;
	int partition;
	String tc_id;
	String classnameMapped;

	public void createTestNgxml() {

		exc = new IOExcel();
		exc.excelSetup("./src/test/java/dataEngine/interface.xlsx");

		// get min max partition data from excel
		totalPartitions = getPartitionCounts();
		minPartition = (int) totalPartitions.get(0);
		maxPartition = (int) totalPartitions.get(1);

		for (int j = minPartition; j <= maxPartition; j++) {//master partition loop
			currentpartition=j;
			System.out.println("Partition "+j);
			XmlSuite mySuite = new XmlSuite();
			mySuite.setName("MyParalleSuite"+currentpartition);
			//mySuite.setParallel(XmlSuite.ParallelMode.TESTS);
			mySuite.setThreadCount(20);

			List<XmlTest> myTests = new ArrayList<XmlTest>();
			
			for (int i = 1; i <= noofrows; i++) {//master test case addition loop
				partition=exc.getExcelIntData(i, 4, "RunList");
				//if((partition==currentpartition)&&(exc.getExcelIntData(i, 4, "RunList").equals("yes")))//check if current row contains tescase for the partition and is active
				if(partition==currentpartition)
				{	
					tc_id=exc.getExcelStringData(i, 1, "RunList");
					classnameMapped=getclassNameFromSheet(tc_id);
				// Create an instance of XmlTest and assign a name for it.
				XmlTest myTest = new XmlTest(mySuite);
				myTest.setName(exc.getExcelStringData(i, 2, "RunList"));//set testcase name from excel
				myTest.addParameter("environment", "itest");

				// Add Class under
				List<XmlClass> myClasses = new ArrayList<XmlClass>();
				myClasses.add(new XmlClass("TestCases."+classnameMapped));//+".class")); 
				//set classes under a test
				
				myTest.setXmlClasses(myClasses);

				// add tests to array list of tests
				myTests.add(myTest);

				System.out.println("Test added: Test" + i);
				
				}

			}
			// add the list of tests to your Suite.
			mySuite.setTests(myTests);

			// Add the suite to the list of suites.
			List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
			mySuites.add(mySuite);

			// Create an instance on TestNG
			TestNG myTestNG = new TestNG();
			myTestNG.setXmlSuites(mySuites);
			mySuite.setFileName("Partition"+currentpartition+".xml");
			// mySuite.setThreadCount(3);

			createXmlFile(mySuite,currentpartition);
			myTestNG.run();
				 
		}
	}

	public void createXmlFile(XmlSuite mSuite) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("./myTemp.xml"));
			writer.write(mSuite.toXml());
			writer.flush();
			writer.close();
			System.out.println("TestNg xml file created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createXmlFile(XmlSuite mSuite, int serial) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("./partition_" + serial + ".xml"));
			writer.write(mSuite.toXml());
			writer.flush();
			writer.close();
			System.out.println("TestNg xml file created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList getPartitionCounts() {
		noofrows = exc.Getrowcount("RunList");
		int max = 0;
		int min = 999;
		int val = 0;
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i <= noofrows; i++) {
			val = exc.getExcelIntData(i, 4, "RunList");
			if (min > val) {
				min = val;
			}
			if (max < val) {
				max = val;
			}

			// System.out.println(exc.getExcelIntData(i, 4, "RunList"));
		}
		result.add(0, min);
		result.add(1, max);

		return result;
	}
	
	public String getclassNameFromSheet(String tcid) {
		System.out.println("Passed tcid "+tcid);
		String result = "none";
		String tc_idfound;
		int rows = exc.Getrowcount("TCMap");
		for (int i = 1; i <= rows; i++) {
			tc_idfound=exc.getExcelStringData(i, 0, "TCMap");
			System.out.println(tc_idfound);
			if(tc_idfound.equalsIgnoreCase(tcid)) {//if tc_id is found in TCMap sheet get the class name
				result=exc.getExcelStringData(i, 1, "TCMap");
			}
		}
		if(result.equalsIgnoreCase("none")) {
			System.out.println("Warning TC seems to be not mapped with classes.Check excel sheet!");
		}
		return result;
	}

}
