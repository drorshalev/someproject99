package com.ibus.autowol.backend;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;

public class NetworkScanner implements IHostEnumerator
{
	public class Result
	{
		public Result(Device device, int progress)
		{
			this.device = device;
			this.progress = progress;
		}
		public Device device;
		public int progress;
	}

	public class Scan extends AsyncTask<Void, Result, Boolean>
	{
		private final String TAG = "HostEnumerator";
		List<OnScanProgressListener> _scanProgressListeners;
		List<OnScanCompleteListener> _scanCompleteListeners; 
		private INetwork _network;
	
		public void setNetwork(INetwork network) {
			_network = network;
		}
		
		public Scan()
		{
			_scanProgressListeners = new ArrayList<OnScanProgressListener>();
			_scanCompleteListeners = new ArrayList<OnScanCompleteListener>(); 
		}
		
		@Override
		public Boolean doInBackground(Void... params) 
		{
			Long start = IpAddress.getUnsignedLongFromString(_network.getNetworkStartIp());
			Long end = IpAddress.getUnsignedLongFromString(_network.getNetworkEndIp());
			List<Device> hosts = null;
			try 
			{
				int ii = 0;
				for (long i = start; i <= end; i++) 
				{
			      Udp.probe(IpAddress.getStringFromLongUnsigned(i));
			      
			      publishProgress(new Result(null, ii++));
			      Thread.sleep(10);
			    }
				
				
				hosts = Arp.EnumerateHosts();
				for(Device h : hosts)
				{
					String n = InetAddressManager.GetHostName(h.getIpAddress());
					if(n == null || n == h.getIpAddress())
						n = Jcifs.getHostName(h.getIpAddress());
					h.setName(n);
					
					publishProgress(new Result(h, 254));
				}
			
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		
			
		    return true;
		}	
		
		@Override
		public void onProgressUpdate (Result... result)
		{
			if(result[0].device != null)
				Log.i(TAG, "updating list item for: " + result[0].device.getIpAddress());
			
			for (OnScanProgressListener listener : _scanProgressListeners) 
			{
				listener.onScanProgress(result[0].device, result[0].progress );
	        }
		}
		
		@Override
		public void onPostExecute (Boolean result)
		{
			Log.i(TAG, "Host enumeration complete");
			
			for (OnScanCompleteListener listener : _scanCompleteListeners) 
			{
				listener.onScanComplete();
	        }
		}
		
		public void addHostSearchProgressListener(OnScanProgressListener listener) {
			_scanProgressListeners.add(listener);
	    }
		
		public void addHostSearchCompleteListener(OnScanCompleteListener listener) {
			_scanCompleteListeners.add(listener);
	    }
	}
	
	Scan currentScan = new Scan();
	
	@Override
	public void scan(INetwork network, OnScanProgressListener progressListener, OnScanCompleteListener completeListener)
	{
		if(currentScan.getStatus() == AsyncTask.Status.RUNNING)
			return;
			
		currentScan = new Scan();
		currentScan.setNetwork(network);
		currentScan.addHostSearchProgressListener(progressListener);
		currentScan.addHostSearchCompleteListener(completeListener);
		currentScan.execute();
	}

	@Override
	public void cancel() 
	{
		if(currentScan.getStatus() == AsyncTask.Status.RUNNING)
			currentScan.cancel(true);
	}

	
}








