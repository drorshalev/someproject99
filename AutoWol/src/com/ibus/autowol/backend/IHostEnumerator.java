package com.ibus.autowol.backend;

import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;

public interface IHostEnumerator  
{
	void scan(INetwork network, OnScanProgressListener progressListener, OnScanCompleteListener completeListener);
	void cancel();
}








