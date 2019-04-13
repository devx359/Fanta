/**
 * @author Debapriyo Haldar
 * 18-Dec-2018
 */
package utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer{
	
	/*Sets the RetryAnalyzer implentation class 
	 * IAnnotationTransformer interface is used to programatically
	 * add annotation to your test methods during run time. Transform method is called for every test during test run
	 * 
	 * */

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		annotation.setRetryAnalyzer(RetryAnalyzer.class);//sets retry analyzer annotation at runtime
		
		
	}

}
