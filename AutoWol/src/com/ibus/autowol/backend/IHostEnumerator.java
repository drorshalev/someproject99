package com.ibus.autowol.backend;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.ibus.autowol.ui.OnScanCompleteListener;
import com.ibus.autowol.ui.OnScanProgressListener;

public interface IHostEnumerator  
{
	void scan(INetwork network, OnScanProgressListener progressListener, OnScanCompleteListener completeListener);
	void cancel();
}








