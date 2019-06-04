package TestCases;


import org.testng.annotations.Test;

import utilities.CreateTestNGFiles;


public class Demo {

	@Test(priority=1)
	public void run() {
		System.out.println("Starting testng file creation");
		CreateTestNGFiles dt = new CreateTestNGFiles();
		dt.createTestNgxml();
	}

}
