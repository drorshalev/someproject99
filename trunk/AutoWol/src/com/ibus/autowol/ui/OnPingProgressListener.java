package com.ibus.autowol.ui;

import com.ibus.autowol.backend.PingResult;


public interface OnPingProgressListener 
{
	public void onPingProgress (PingResult result);
}
