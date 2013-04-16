package com.ibus.autowol.backend;

import java.io.Serializable;


public class Host implements Serializable
{
	public enum HostType
	{
		PC,
		Gateway
	}
	private static final long serialVersionUID = 1L;
	private int primaryKey;
	private int deviceTypeImage;
	private HostType deviceType;
	private String ipAddress;
	private String macAddress;
	private String name;
	private String displayName;
	private String nicVendor;
	

	// Properties //////////////////////////////////////////////////////////

	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public HostType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(HostType hostType) {
		this.deviceType = hostType;
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
	public int getDeviceTypeImage() {
		return deviceTypeImage;
	}
	public void setDeviceTypeImage(int deviceTypeImage) {
		this.deviceTypeImage = deviceTypeImage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNicVendor() {
		return nicVendor;
	}
	public void setNicVendor(String nicName) {
		this.nicVendor = nicName;
	}
	
	
	
	// Constructors //////////////////////////////////////////////////////////
	
	public Host()
	{
		setDeviceType(HostType.PC);
	}
	
	public Host(int primaryKey, String name, String displayName, String ipAddress, String  macAddress, String vendor)
	{
		setDeviceType(HostType.PC);
		setName(name);
		setDisplayName(displayName);
		setIpAddress(ipAddress);
		setMacAddress(macAddress);
		setNicVendor(vendor);
	}
	
	
	public Host(String ip)
	{
		this();
		setIpAddress(ip);
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + deviceTypeImage;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((macAddress == null) ? 0 : macAddress.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nicVendor == null) ? 0 : nicVendor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Host other = (Host) obj;
		if (deviceType != other.deviceType)
			return false;
		if (deviceTypeImage != other.deviceTypeImage)
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (macAddress == null) {
			if (other.macAddress != null)
				return false;
		} else if (!macAddress.equals(other.macAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nicVendor == null) {
			if (other.nicVendor != null)
				return false;
		} else if (!nicVendor.equals(other.nicVendor))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}

   
