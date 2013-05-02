package com.ibus.autowol.backend;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;

public class DevicePinger implements IPinger
{
	private final String TAG = "AutoWol-NetworkScanner";

	public class Result
	{
		public Result(Device device, boolean success)
		{
			this.device = device;
			this.success = success;
		}
		public Device device;
		public boolean success;
	}
	
	public class Pinger extends AsyncTask<Void, Result, Boolean>
	{
		List<OnPingCompleteListener> _pingCompleteListeners;
		List<OnPingProgressListener> _pingProgressListeners;
		List<Device> _devices;
		
		public Pinger()
		{}
		
		public Pinger(List<Device> devices)
		{
			_devices =devices;
			_pingCompleteListeners = new ArrayList<OnPingCompleteListener>();
			_pingProgressListeners = new ArrayList<OnPingProgressListener>();
		}
		
		@Override
		public Boolean doInBackground(Void... params) 
		{
			for(Device d : _devices)
			{
				boolean s = Shell.ping(d.getIpAddress());
				publishProgress(new Result(d, s));
			}
			
		    return true;
		}	
		
		@Override
		public void onProgressUpdate (Result... result)
		{
			Log.i(TAG, "Ping complete");
			
			for (OnPingProgressListener listener : _pingProgressListeners) 
			{
				listener.onPingProgress(result[0].device, result[0].success);
	        }
		}
		
		@Override
		public void onPostExecute (Boolean result)
		{
			Log.i(TAG, "Ping complete");
			
			for (OnPingCompleteListener listener : _pingCompleteListeners) 
			{
				listener.onPingComplete(result);
	        }
		}
		
		public void addOnPingCompleteListener(OnPingCompleteListener listener) {
			_pingCompleteListeners.add(listener);
	    }
		
		public void addOnPingProgressListener(OnPingProgressListener listener) {
			_pingProgressListeners.add(listener);
	    }
	}
	
	Pinger pinger = new Pinger();
	
	@Override
	public void ping(List<Device> devices, OnPingCompleteListener completeListener, OnPingProgressListener progressListener)
	{
		if(pinger.getStatus() == AsyncTask.Status.RUNNING){
			Log.i(TAG, "Network scan failed: scan thread already running");
			return;
		}
		pinger = new Pinger(devices);
		pinger.addOnPingCompleteListener(completeListener);
		pinger.addOnPingProgressListener(progressListener);
		pinger.execute();
	}

	/*@Override
	public void ping(Device device, OnPingCompleteListener completeListener, OnPingProgressListener progressListener)
	{
		List<Device> devices = new ArrayList<Device>();
		devices.add(device);
		
		ping(devices, completeListener, progressListener);
	}*/
	
	
	@Override
	public void cancel() 
	{
		if(pinger.getStatus() == AsyncTask.Status.RUNNING)
			pinger.cancel(true);
		
		Log.i(TAG, "Network scan canceled");
	}

	
}








