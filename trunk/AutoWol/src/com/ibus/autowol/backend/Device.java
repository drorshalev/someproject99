package com.ibus.autowol.backend;

import java.io.Serializable;


public class Device implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String displayName;	
	protected int primaryKey;
	protected String ipAddress;
	protected String macAddress;	
	protected String nicVendor;
	
	
	// Properties //////////////////////////////////////////////////////////

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String hardwareAddress) {
		this.macAddress = hardwareAddress;
	}
	public String getNicVendor() {
		return nicVendor;
	}
	public void setNicVendor(String nicName) {
		this.nicVendor = nicName;
	}
	
	// Constructors //////////////////////////////////////////////////////////
	
	public Device(){
	}
	
	public Device(String ip)
	{
		this();
		setIpAddress(ip);
	}

	
	
}

   
