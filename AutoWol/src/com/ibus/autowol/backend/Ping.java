package com.ibus.autowol.backend;

import java.io.IOException;
import android.util.Log;

public abstract class Ping 
{
	public static boolean isReachable(String ipAddress)
	{
		if(ipAddress == null)
    		throw new IllegalArgumentException("ipAddress cannot be null");
    	else if(!IpAddress.isValidIp(ipAddress))
    		throw new IllegalArgumentException("ipAddress is not a valid ip address");
		
		Process p1;
		try 
		{
			p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + ipAddress);
			int returnVal = p1.waitFor();
			boolean reachable = (returnVal==0);
			
			if(reachable){
				Log.i("HostEnumerator.Pinged", ipAddress +  " was pinged successfully");
				return true;
			}
			else
				Log.i("HostEnumerator", ipAddress +  " was NOT pinged successfully");
			
		} catch (IOException e) {
			Log.e("HostEnumerator", "an IOException occured while attempting to ping: " + ipAddress );
		} catch (InterruptedException e) {
			Log.e("HostEnumerator", "an InterruptedException occured while attempting to ping: " + ipAddress );
		}
		
		
		return false;
		
	}
	
	
	/*this seems to work differently to the native ping available in a linux / windows console.  will fail on a windows host that has a a firewall up*/
	/*public boolean javaPing(String ip)
	{
		try 
		{
			InetAddress in = InetAddress.getByName(ip);
			
			 if (in.isReachable(5000)) 
			 {
				 String h = in.getHostName();
				 
				 Log.i("HostEnumerator.Pinged", ip +  " was pinged successfully"); 
				 return true;
			 }
			 else
			 {
				 Log.i("HostEnumerator", ip +  " was NOT pinged successfully");
			 }
			
		} catch (UnknownHostException e) {
			Log.e("HostEnumerator", "an UnknownHostException occured while attempting to ping: " + ip );
		} catch (IOException e) {
			Log.e("HostEnumerator", "an IOException occured while attempting to ping: " + ip );
		}
		
		return false;
	}*/
	
	
	
}
