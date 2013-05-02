package com.ibus.autowol.ui;

import com.ibus.autowol.backend.Device;


public interface OnPingProgressListener 
{
	public void onPingProgress (Device device, boolean success);
}
