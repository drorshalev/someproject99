package com.ibus.autowol.backend;

import java.net.UnknownHostException;

import android.util.Log;

import jcifs.netbios.NbtAddress;

public abstract class Jcifs 
{
	//this will only work on pcs that have samaba / cifs enabled
	public static Host GetHost(String ipAddress)
	{
		throwOnIllegalIp(ipAddress);
		
		Host host = null;
		try 
		{
			NbtAddress[] nbEntry = NbtAddress.getAllByAddress(ipAddress);
			
			for(int i=0; i < nbEntry.length; i++)
			{
				if(!nbEntry[i].isGroupAddress())
				{
					host = new Host();
					host.setIpAddress(ipAddress);
					host.setName(nbEntry[i].getHostName());
					host.setMacAddress(MacAddress.getStringFromBytes(nbEntry[i].getMacAddress()));
					
					Log.i("NetbiosAddress", String.format("Netbios Address found. ip: %s, name: %s, mac: %s ", host.getIpAddress(), host.getName(), host.getMacAddress()));
				}
			}
			
		} catch (UnknownHostException e) 
		{
			Log.e("NetbiosAddress", "an UnknownHostException occured while attempting to get host name for: " +  ipAddress );
		}
		
		return host;
	}
	
	public static String getHostName(String ipAddress)
	{
		Host host = GetHost(ipAddress);
		
		if(host != null)
			return host.getName();
		
		return null;
	}
	
	
	
	
	private static void throwOnIllegalIp(String ipAsString)
	{
		if(ipAsString == null)
    		throw new IllegalArgumentException("ipAsString cannot be null");
    	else if(!IpAddress.isValidIp(ipAsString))
    		throw new IllegalArgumentException("ipAsString is not a valid ip address");
	}

}
