package TestCases;

import org.testng.annotations.Test;

public class JustDemo {
	
	@Test (priority = 1)
	public void meth1() {
		System.out.println("inside justdemo test 1");
	}
	
	@Test (priority = 2)
	public void meth2() {
		System.out.println("inside justdemo test 1");
	}

}
