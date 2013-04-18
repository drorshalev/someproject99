package com.ibus.autowol.backend;


public abstract class MacAddress 
{
	private final static String TAG = "MacAddress";
	private static final String NOMAC = "00:00:00:00:00:00";
										 
	public static boolean isValidMac(String macString)
	{
		if(macString == null)
			return false;
		
		return !(macString.equals(NOMAC));
	}
	
	public static String getStringFromBytes(byte[] macBytes)
	{
		StringBuilder sb = new StringBuilder(18);
		
	    for (byte b : macBytes) 
	    {
	        if (sb.length() > 0)
	            sb.append('-');
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
	
}









