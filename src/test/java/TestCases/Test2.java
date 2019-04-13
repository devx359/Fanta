/**
 * Author:Debapriyo Haldar	
 * 31-Dec-2018
 */
package TestCases;

import org.testng.annotations.Test;

import utilities.IOExcel;

public class Test2 {
	
	@Test
	public void meth ()
	{
		System.out.println("Before function call");
		System.out.println(fun("RAKESH","NAYAK"));
		System.out.println("After function call");
		
		Test3 obj = new Test3();
		obj.meth2();
		obj.meth3(25);
		

		
			
	}
	
	public String fun (String a,String b)
	{
		String result ;
		result=b+a;
		return result;
		
		
		
	}

}
