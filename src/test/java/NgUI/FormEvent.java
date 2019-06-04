package NgUI;

import java.util.EventObject;

public class FormEvent extends EventObject {

	private String suite;
	private String classname;
	private String testname;

	public FormEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public FormEvent(Object source, String suite, String classname, String testname) {
		super(source);
		this.suite=suite;
		this.classname=classname;
		this.testname=testname;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

}
