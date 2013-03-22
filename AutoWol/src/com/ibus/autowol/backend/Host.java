package com.ibus.autowol.backend;

import java.io.Serializable;


public class Host implements Serializable
{
	private static final long serialVersionUID = 1L;
	private HostType deviceType;
	private int deviceTypeImage;
	
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
		return true;
	}

	private IpAddress ipAddress;
	private MacAddress macAddress;
	
	public enum HostType
	{
		PC,
		Gateway
	}

	// Properties //////////////////////////////////////////////////////////

	public HostType getHostType() {
		return deviceType;
	}
	public void setHostType(HostType hostType) {
		this.deviceType = hostType;
	}
	public IpAddress getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(IpAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	public MacAddress getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(MacAddress hardwareAddress) {
		this.macAddress = hardwareAddress;
	}
	public int getDeviceTypeImage() {
		return deviceTypeImage;
	}
	public void setDeviceTypeImage(int deviceTypeImage) {
		this.deviceTypeImage = deviceTypeImage;
	}
	// Constructors //////////////////////////////////////////////////////////
	
	
	public Host()
	{
		setHostType(HostType.PC);
		setIpAddress(new IpAddress());
		setMacAddress(new MacAddress());
	}
	
	public Host(IpAddress ip)
	{
		this();
		setIpAddress(ip);
	}
	
	
	// Methods //////////////////////////////////////////////////////////
	
	
	
	
}



/*public boolean isAlive = false;
public int position = 0;
public int responseTime = 0; // ms
public String hostName = null;
public String nicVendor = "Unknown";
public String os = "Unknown";
public HashMap<Integer, String> services = null;
public HashMap<Integer, String> banners = null;
public ArrayList<Integer> portsOpen = null;
public ArrayList<Integer> portsClosed = null;*/
   
