package com.ibus.autowol.backend;

import java.util.List;

import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;

public interface IPinger 
{
	public void ping(List<Device> devices, OnPingCompleteListener completeListener, OnPingProgressListener progressListener);
	//public void ping(Device device, OnPingCompleteListener completeListener, OnPingProgressListener progressListener);
	public void cancel();
}
