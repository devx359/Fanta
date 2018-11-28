package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixTimeStampConverter {

	public String convert(String unixtimestamp) {
		
		String dateString=null;
		try {
			//System.out.println("inside convereter");
			Date time = new java.util.Date((long) Integer.parseInt(unixtimestamp) * 1000);
			dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
			//System.out.println(dateString);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("unixtimestamp conversion issue "+e);
		}

		return dateString;
	}

}
