package com.ibus.autowol.backend;


public class Host 
{
	private HostType deviceType;
	private int deviceTypeImage;
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
   
