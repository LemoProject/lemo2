package de.lemo.dms.connectors;

import java.security.MessageDigest;

public class Encoder {

	private static MessageDigest md = null;
	/**
	 * Returns the MD5 value of the input-string.
	 * 
	 * @param in String to encode
	 * @return MD5-encoded string
	 */
	public static String createMD5(String in)
	{
		try{
			md = MessageDigest.getInstance("MD5");
		    md.update(in.getBytes());
		
		    byte byteData[] = md.digest();
		    
		    StringBuffer sb = new StringBuffer();
		    
		    for (int i = 0; i < byteData.length; i++) {
		    	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    in = sb.substring(0);
		}catch (Exception e)
		{}
	    return in;
	}
}