package com.ibus.autowol.backend;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;

public class PersistantPinger implements Runnable, IPinger
{
	private final String TAG = "AutoWol-PersistantPinger";
	private final MessageHandler _messageHandler = new MessageHandler();
	private Thread _pingThread;
	private boolean _continue;
	private List<Device> _devices = new ArrayList<Device>();
	private static int UPDATE_PROGRESS = 1;
	//private static int UPDATE_COMPLETE = 2;
	
	
	//handler used by the thread below to marshel messages back to the main ui thread. it is probably not neccessary 
	//to declare this class static since it is not within scope of the activity so it shouldnt leak the activity?
	//weak references ensure that our progress listeners can get garbage collected and dont leak
	private static class MessageHandler extends Handler 
	{
		private final String TAG = "AutoWol-PersistantPinger";
		private WeakReference<OnPingProgressListener> _progressListenerReference;
		private WeakReference<OnPingCompleteListener> _completeListenerReference;

		@Override
		public void handleMessage(Message msg) 
		{
			Log.i(TAG, "Entering handleMessage");	
			
			if(msg.what == UPDATE_PROGRESS)
			{
				OnPingProgressListener l = _progressListenerReference.get();
				if (l != null) 
				{
					Log.i(TAG, "progress listener found");
					ThreadResult res = (ThreadResult)msg.obj;
					l.onPingProgress(res);
				}
				else
				{
					//the view that this handler updates no longer exists
					Log.i(TAG, "progress listener is null / not found");
				}
			}
			else
			{
				OnPingCompleteListener l = _completeListenerReference.get();
				if (l != null) 
				{
					Log.i(TAG, "progress listener found");
					l.onPingComplete(true);
				}
				else
				{
					//the view that this handler updates no longer exists
					Log.i(TAG, "progress listener is null / not found");
				}
			}
		}
		
		public void addOnPingCompleteListener(OnPingCompleteListener listener) {
			_completeListenerReference = new WeakReference<OnPingCompleteListener>(listener);
	    }
		
		public void addOnPingProgressListener(OnPingProgressListener listener) {
			_progressListenerReference = new WeakReference<OnPingProgressListener>(listener);
	    }
	}

	

	@Override
	public void run() 
	{
		Log.i(TAG, "THREAD ENTERED ....");
		
		while(getContinue())
		{
			List<Device> dl = getDevices();

			for(Device d : dl)
			{
				boolean s = InetAddressManager.ping(d.getIpAddress(), 300);
				if(!s)
					s = Shell.ping(d.getIpAddress());	
				
				Message msg = _messageHandler.obtainMessage(UPDATE_PROGRESS, new ThreadResult(d, s));
				_messageHandler.sendMessage(msg);	
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.i(TAG, "Thread work performed");
		}
		
		
		Log.i(TAG, "exiting thread");
	}	
	
	
	
	
	/*@Override
	public void run() 
	{

		Log.i(TAG, "PersistantPinger Thread entered ....");
		

		while (getContinue()) 
		{
			ExecutorService executor = Executors.newFixedThreadPool(5);
			List<Future<ThreadResult>> list = new ArrayList<Future<ThreadResult>>();

			List<Device> dl = getDevices();

			for (Device d : dl) 
			{
				Callable<ThreadResult> worker = new HostEnumerationCallable(d);
				Future<ThreadResult> submit = executor.submit(worker);
				list.add(submit);
			}

			// Now retrieve the result
			for (Future<ThreadResult> future : list) 
			{
				try 
				{
					ThreadResult res = future.get();
					Log.i(TAG, "Completed pinging " + res.device.getIpAddress());
					
					Message msg = _messageHandler.obtainMessage(UPDATE_PROGRESS, res);
					_messageHandler.sendMessage(msg);					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			executor.shutdown();

			try 
			{
				Log.i(TAG, "PersistantPinger Thread is sleeping for 5 seconds");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Log.i(TAG, "exiting PersistantPinger thread");
	}
	*/
	
	/*public class HostEnumerationCallable implements Callable<ThreadResult> 
	{
		Device _device;
		
		public HostEnumerationCallable(Device device)
		{
			_device = device.getCopy();
		}
		
		@Override
		public ThreadResult call() throws Exception 
		{
			ThreadResult result = new ThreadResult(_device);
			
			result.success = InetAddressManager.ping(_device.getIpAddress(), 300);
			if(!result.success)
			{
				result.success = Shell.ping(_device.getIpAddress());	
			}
			
			return result;
		}
	
	}*/
	
	
	
	
	
	@Override
	public void start(List<Device> devices) 
	{
		if(_pingThread == null || !_pingThread.isAlive())
		{
			setContinue(true);
			setDevices(devices);
			_pingThread = new Thread(this);
			_pingThread.setDaemon(true);
			_pingThread.start();
		}
	}

	
	@Override
	public void stop() 
	{
		setContinue(false);
	}
	
	
	private List<Device> getDevices()
	{
		synchronized (this)
		{
			List<Device> dl = new ArrayList<Device>();
			for(Device d : _devices)
				dl.add(d.getCopy());
			
			return dl;
		}
	}
	
	public void setDevices(List<Device> devices)
	{
		synchronized (this)
		{
			_devices = devices;
		}
	}
	
	private boolean getContinue()
	{
		//when a thread enteres here no other thread may enter in 
		//getContinue or setContinue
		synchronized (this)
		{
			return _continue;
		}
	}
	
	private void setContinue(boolean continueThread)
	{
		synchronized (this)
		{
			_continue = continueThread;
		}
	}
	
	public void addOnPingCompleteListener(OnPingCompleteListener listener) {
		_messageHandler.addOnPingCompleteListener(listener);
    }
	
	public void addOnPingProgressListener(OnPingProgressListener listener) {
		_messageHandler.addOnPingProgressListener(listener);
    }



	
}
