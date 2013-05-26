package com.ibus.autowol.backend;

public class PingResult 
{
	public PingResult(Device device, boolean success)
	{
		this.device = device;
		this.success = success;
	}
	public Device device;
	public boolean success;
}
