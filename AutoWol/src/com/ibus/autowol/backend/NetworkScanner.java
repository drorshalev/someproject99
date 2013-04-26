package com.ibus.autowol.backend;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;

public class NetworkScanner implements IHostEnumerator
{
	private final String TAG = "AutoWol-NetworkScanner";
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
			Log.i(TAG, "starting network scan with ip start: " + _network.getNetworkStartIp() + ". ip end:" + _network.getNetworkEndIp());
			
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
				
				Log.i(TAG, "successfully completed probing devices on network");
				
				hosts = Arp.EnumerateHosts();
				for(Device h : hosts)
				{
					String n = InetAddressManager.GetHostName(h.getIpAddress());
					if(n == null || n == h.getIpAddress())
						n = Jcifs.getHostName(h.getIpAddress());
					h.setName(n);
				
					publishProgress(new Result(h, 254));
				}
				
				Log.i(TAG, "successfully completed search for devices in arp cache");
			
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		
			
		    return true;
		}	
		
		@Override
		public void onProgressUpdate (Result... result)
		{
			for (OnScanProgressListener listener : _scanProgressListeners) 
			{
				listener.onScanProgress(result[0].device, result[0].progress );
	        }
		}
		
		@Override
		public void onPostExecute (Boolean result)
		{
			Log.i(TAG, "Network scan complete");
			
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
		if(currentScan.getStatus() == AsyncTask.Status.RUNNING){
			Log.i(TAG, "Network scan failed: scan thread already running");
			return;
		}
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
		
		Log.i(TAG, "Network scan canceled");
	}

	
}








