package com.ibus.autowol.backend;

import java.io.Serializable;


public class Router extends Device implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String bssid;
	private String ssid;
	
	// Properties //////////////////////////////////////////////////////////

	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	
	// Constructors //////////////////////////////////////////////////////////
	
	public Router(){
	}
	
	public Router(String ip)
	{
		this();
		setIpAddress(ip);
	}
	
	
	
	
}

   
