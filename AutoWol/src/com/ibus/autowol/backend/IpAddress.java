package com.ibus.autowol.backend;

import java.io.Serializable;

public class IpAddress implements Serializable 
{
	private static final long serialVersionUID = 1L;
	public static final String NOIP = "0.0.0.0";
    public static final String NOMASK = "255.255.255.255";
    private String _address;
    
    // Properties ///////////////////////////////////////////////
    
    public String getAddress() {
		return _address;
	}
    public void setAddress(String address) {
    	_address = address;
	}
    
    
    // Constructors ///////////////////////////////////////////////
	
    public IpAddress(){
    	setAddress(NOIP);	
    }
    
    public IpAddress(String address){
    	setAddress(address);
    }

    public IpAddress(int address){
    	setAddress(address);
    }
    
    public IpAddress(long address){
    	setAddress(address);
    }
    
    
    // Methods ///////////////////////////////////////////////
    
    public Long toLong(){
    	return IpAddress.getUnsignedLongFromIp(_address);
    }
    
    public void setAddress(int address) {
    	_address = IpAddress.getIpFromIntSigned(address);
	}
    
    public void setAddress(long address){
    	_address = IpAddress.getIpFromLongUnsigned(address);
    }
    
    public boolean isEmptyIp(){
    	return _address.equals(NOIP);
    }
    
    public boolean isEmptyNetworkMask(){
    	return _address.equals(NOMASK);
    }
    
    public static long getUnsignedLongFromIp(String ip_addr) 
    {
        String[] a = ip_addr.split("\\.");
        return (Integer.parseInt(a[0]) * 16777216 + Integer.parseInt(a[1]) * 65536
                + Integer.parseInt(a[2]) * 256 + Integer.parseInt(a[3]));
    }

    public static String getIpFromIntSigned(int ip_int) 
    {
        String ip = "";
        for (int k = 0; k < 4; k++) {
            ip = ip + ((ip_int >> k * 8) & 0xFF) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }

    public static String getIpFromLongUnsigned(long ip_long) 
    {
        String ip = "";
        for (int k = 3; k > -1; k--) {
            ip = ip + ((ip_long >> k * 8) & 0xFF) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }
  
	@Override
	public boolean equals(Object o) 
	{
		if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof IpAddress))return false;
	    
	    IpAddress ad = (IpAddress)o;
		return ad.getAddress().equals(getAddress());
	}
	
	@Override
	public int hashCode() 
	{
		return getAddress().hashCode();
	}
    
}







