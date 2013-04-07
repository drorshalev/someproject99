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
	private int deviceTypeImage;
	private HostType deviceType;
	private String ipAddress;
	private String macAddress;
	private String name;
	private String nicName;
	

	// Properties //////////////////////////////////////////////////////////

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
	public String getNicName() {
		return nicName;
	}
	public void setNicName(String nicName) {
		this.nicName = nicName;
	}
	
	
	
	// Constructors //////////////////////////////////////////////////////////
	
	public Host()
	{
		setDeviceType(HostType.PC);
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
		result = prime * result + ((nicName == null) ? 0 : nicName.hashCode());
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
		if (nicName == null) {
			if (other.nicName != null)
				return false;
		} else if (!nicName.equals(other.nicName))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}

   
