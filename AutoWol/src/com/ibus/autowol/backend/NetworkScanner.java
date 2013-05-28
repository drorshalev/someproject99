package com.ibus.autowol.backend;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ibus.autowol.backend.PersistantPinger.MessageHandler;
import com.ibus.autowol.ui.OnPingCompleteListener;
import com.ibus.autowol.ui.OnPingProgressListener;
import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;

public class NetworkScanner implements IHostEnumerator
{
	private final String TAG = "AutoWol-NetworkScanner";
	private final MessageHandler _messageHandler = new MessageHandler();
	private Thread _scannerThread;
	private boolean _continue;
	//private List<Device> _devices = new ArrayList<Device>();
	private static int UPDATE_PROGRESS = 1;
	private static int UPDATE_COMPLETE = 2;
	
	
	//handler used by the thread below to marshel messages back to the main ui thread. it is probably not neccessary 
	//to declare this class static since it is not within scope of the activity so it shouldnt leak the activity?
	//weak references ensure that our progress listeners can get garbage collected and dont leak
	private static class MessageHandler extends Handler 
	{
		private final String TAG = "AutoWol-NetworkScanner";
		private WeakReference<OnScanProgressListener> _progressListenerReference;
		private WeakReference<OnScanCompleteListener> _completeListenerReference;
		
		@Override
		public void handleMessage(Message msg) 
		{
			Log.i(TAG, "Entering handleMessage");	
			
			if(msg.what == UPDATE_PROGRESS)
			{
				OnScanProgressListener l = _progressListenerReference.get();
				if (l != null) 
				{
					Log.i(TAG, "progress listener found");
					ThreadResult res = (ThreadResult)msg.obj;
					l.onScanProgress(res);
				}
				else
				{
					//the view that this handler updates no longer exists
					Log.i(TAG, "progress listener is null / not found");
				}
			}
			else
			{
				OnScanCompleteListener l = _completeListenerReference.get();
				if (l != null) 
				{
					Log.i(TAG, "progress listener found");
					l.onScanComplete();
				}
				else
				{
					//the view that this handler updates no longer exists
					Log.i(TAG, "progress listener is null / not found");
				}
			}
		}
		
		public void addOnScanProgressListener(OnScanProgressListener listener) {
			_progressListenerReference = new WeakReference<OnScanProgressListener>(listener);
	    }
		
		public void addOnScanCompleteListener(OnScanCompleteListener listener) {
			_completeListenerReference = new WeakReference<OnScanCompleteListener>(listener);
	    }
	}
	
	

	@Override
	public void scan(INetwork network, OnScanProgressListener progressListener,
			OnScanCompleteListener completeListener) {

		
	}

	@Override
	public void cancel() {

		
	}
	
	
}








