package com.ibus.autowol.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public abstract class Arp 
{
	static String arpFileLocation = "/proc/net/arp";
	private final static String TAG = "Arp";
    private final static int BUF = 8 * 1024;
	
	
	/*
		Format of the arp file that we need to parse:
		
		IP address       HW type     Flags       HW address            Mask     Device
		10.0.0.138       0x1         0x2         08:76:ff:02:7c:c0     *        wlan0
		10.0.0.5         0x1         0x0         00:00:00:00:00:00     *        wlan0
	*/
	public static List<Host> EnumerateHosts()
	{
		List<Host> hosts = new ArrayList<Host>();
		
		//String hw = NOMAC;
		try 
		{
	        BufferedReader bufferedReader = new BufferedReader(new FileReader(arpFileLocation), BUF);
	        boolean firstLine = true;
	        String line;
	        
	        while ((line = bufferedReader.readLine()) != null) 
	        {
	        	String[] parts = null;
	        	
	        	//skip first line as it only contains the header
	        	if(!firstLine)
	        	{
	        		parts = line.split("\\s+");
	        		
	        		Host h = new Host();
	        		h.setIpAddress(parts[0]);
	        		h.setMacAddress(parts[3]);
	        		h.setNicName(parts[5]);
	        	}
	        	
	        	
	        	firstLine = false;
	        	
	            /*matcher = pattern.matcher(line);
	            if (matcher.matches()) {
	                hw = matcher.group(1);
	                break;
	            }*/
	        }
	        bufferedReader.close();
	    
		    
		} catch (IOException e) {
		    Log.e(TAG, "Can't read Arp file: " + e.getMessage());
		    
		}
		
		
		return null;
		
	}
	
}











