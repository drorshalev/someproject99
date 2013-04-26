package com.ibus.autowol.mock;

import java.util.ArrayList;
import java.util.List;

import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.IHostEnumerator;
import com.ibus.autowol.backend.INetwork;
import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;


public class MockNetworkScanner implements IHostEnumerator 
{
	List<OnScanProgressListener> _scanProgressListeners = new ArrayList<OnScanProgressListener>();
	List<OnScanCompleteListener> _scanCompleteListeners = new ArrayList<OnScanCompleteListener>();
	
	@Override
	public void scan(INetwork network, OnScanProgressListener progressListener, OnScanCompleteListener completeListener) 
	{
		_scanProgressListeners.add(progressListener);
		_scanCompleteListeners.add(completeListener);
		
		Device h1 = new Device();
		h1.setNicVendor("nic vendor 1");
		h1.setName("Host Name 1");
		h1.setMacAddress("00:00:00:00:00:01");
		h1.setIpAddress("10.0.0.101");
		h1.setDisplayName("Host Name 1");
		
		Device h2 = new Device();
		h2.setNicVendor("nic vendor 2");
		h2.setName("Host Name 2");
		h2.setMacAddress("00:00:00:00:00:02");
		h2.setIpAddress("10.0.0.102");
		h2.setDisplayName("Host Name 2");
		
		Device h3 = new Device();
		h3.setNicVendor("nic vendor 3");
		h3.setName("Host Name 3");
		h3.setMacAddress("00:00:00:00:00:03");
		h3.setIpAddress("10.0.0.103");
		h3.setDisplayName("Host Name 3");
		
		_scanProgressListeners.get(0).onScanProgress(h1, 1);
		_scanProgressListeners.get(0).onScanProgress(h2, 100);
		_scanProgressListeners.get(0).onScanProgress(h3, 254);
		
		_scanCompleteListeners.get(0).onScanComplete();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}









