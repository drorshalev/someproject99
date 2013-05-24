package com.ibus.autowol.backend;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;

public class PersistantPinger implements IPinger, Runnable
{
	private final String TAG = "AutoWol-PersistantPinger";
	private Thread _pingThread;
	private final MessageHandler _messageHandler = new MessageHandler(this);
	
	private static class MessageHandler extends Handler 
	{
		private final String TAG = "AutoWol-PersistantPinger";
		private final WeakReference<PersistantPinger> _pingerReference;

		public MessageHandler(PersistantPinger pinger) 
		{
			_pingerReference = new WeakReference<PersistantPinger>(pinger);
		}

		@Override
		public void handleMessage(Message msg) 
		{
			Log.i(TAG, "Running from handleMessage");	
			
			PersistantPinger p = _pingerReference.get();
			if (p != null) 
			{
					 
			}
		}
	}

	
	

	@Override
	public void run() 
	{
		Log.i(TAG, "Running from thread");
		
		Device d = new Device();
		d.setDisplayName("hello");
		
		Message msg = _messageHandler.obtainMessage(1, d);
		_messageHandler.sendMessage(msg);
	}	
	
	
	
	@Override
	public void ping(List<Device> devices, OnPingCompleteListener completeListener, OnPingProgressListener progressListener) 
	{
		_pingThread = new Thread(this);
		_pingThread.start();
	}

	
	@Override
	public void cancel() {
	
		//_pingThread.
	}
	
	
	

}
