package utilities;

import org.apache.commons.codec.binary.Base64;

public class Base64EncryptionUtil {
	
	public String decode(String data)
	{
		byte[] decodedBytes = Base64.decodeBase64(data);
		return new String(decodedBytes);
	}
	
	public String encode(String data)
	{
		byte[] encodedBytes = Base64.encodeBase64(data.getBytes());
		return new String(encodedBytes);
	}

}
