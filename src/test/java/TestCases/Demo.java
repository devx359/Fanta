package TestCases;

import org.testng.annotations.Test;
import org.apache.commons.codec.digest.DigestUtils;


public class Demo {
	
	
	@Test
	public void Test2()
	{

		String key ="AAAAB3NzaC1yc2EAAAABJQAAAQEAvXtxSUBowXj1KHsytynVsevHEHz9N2uIvAbEFnZZVA2ij91eC62GO4Gt95rGp7CwMF02a8hj6OtB8S2PDCMauJh+S82+9Ym2QOwnIE6qrDb6QdlR0AZhzX+0hgmfOpcByOxpbi1e58kuLn";
		String data= "{\"acl\":\"1st Operator\",\"identity\":\"I0166\",\"project_code\":\"LGTT_001\",\"sub_project_code\":\"LGTT_001_1\",\"role\":\"OTHER\"}";
		//String data="{\"identity\":\"I0166\",\"acl\":\"1st Operator\",\"project_code\":\"CWF_001\",\"sub_project_code\":\"CWF_001\",\"role\":\"OTHER\",\"third_party\":\"F8\"}";	
		//String dr="{\"identity\":\"+emp+"\",\"acl\":\"1st Operator\",\"project_code\":\"CWF_001\",\"sub_project_code\":\"CWF_001\",\"role\":\"OTHER\",\"third_party\":\"F8\"}";
				
		//		"{\"identity\":\""+key+"\",\"acl\":\"1st Operator\",\"project_code\":\"LLA_001\",\"sub_project_code\":\"LLA_001_1\",\"role\":\"OTHER\"}";
		
		/*JSONObject jsonreq = new JSONObject();
			jsonreq.put("acl", "Manager");
			jsonreq.put("identity", "I2034");
			jsonreq.put("project_code", "LLA_001");
			jsonreq.put("sub_project_code", "LLA_001_1");
			jsonreq.put("role", "OTHER");*/
	//	String data="{\"identity\":\""+data+"\",\"acl\":\"1st Operator\",\"project_code\":\"LLA_001\",\"sub_project_code\":\"LLA_001_1\",\"role\":\"OTHER\"}";
			
		//	jsonreq.p
			//String jval = JSONValue.toJSONString (lset);
			//String val = jsonreq.toJSONString();
			System.out.println(data);
			String sha = DigestUtils.sha1Hex(data+key);
			System.out.println(sha);
		
	}
}
