package com.ibus.autowol.backend;

public class ThreadResult 
{
	public ThreadResult(Device device, boolean success)
	{
		this.device = device;
		this.success = success;
	}
	public Device device;
	public boolean success;
}
