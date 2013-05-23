package com.ibus.autowol.backend;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;

public class Pinger implements IPinger
{
	private Thread _pingThread;
	
	public class PingRunnable implements Runnable
	{
		@Override
		public void run() 
		{
			Device d = new Device();
			d.setDisplayName("hello");
			
			Message msg = _messageHandler.obtainMessage(1, d);
			_messageHandler.sendMessage(msg);
		}	
	}
	
	private static class MessageHandler extends Handler 
	{
		private final WeakReference<Pinger> _pingerReference;

		public MessageHandler(Pinger pinger) 
		{
			_pingerReference = new WeakReference<Pinger>(pinger);
		}

		@Override
		public void handleMessage(Message msg) 
		{
			Pinger p = _pingerReference.get();
			if (p != null) {
				 
			}
		}
	}

	private final MessageHandler _messageHandler = new MessageHandler(this);
	

	
	
	
	
	@Override
	public void ping(List<Device> devices, OnPingCompleteListener completeListener, OnPingProgressListener progressListener) 
	{
		_pingThread = new Thread(new PingRunnable());
		_pingThread.start();
	}

	
	@Override
	public void cancel() {
	
		//_pingThread.
	}
	
	
	

}
