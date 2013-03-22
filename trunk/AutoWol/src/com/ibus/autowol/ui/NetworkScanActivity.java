package com.ibus.autowol.ui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.MainActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.IpAddress;
import com.ibus.autowol.backend.NetInfo;

public class NetworkScanActivity extends SherlockFragmentActivity implements OnHostSearchCompleteListener, OnHostSearchProgressListener
{
	NetworkScanFragment _fragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_scan_activity);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		CreateFragment();
	}
	
	public void CreateFragment()
	{
		_fragment = (NetworkScanFragment)SherlockFragment.instantiate(this, NetworkScanFragment.class.getName()); 
		  
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.network_scan_activity_fragment_container, _fragment);
		ft.commit();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		int pbEnd =(int)(IpAddress.getUnsignedLongFromIp(NetInfo.network_end.getAddress()) - IpAddress.getUnsignedLongFromIp(NetInfo.network_start.getAddress()));
		ProgressBar pb = (ProgressBar) findViewById(R.id.network_scan_activity_progress_bar);
		TextView pbText = (TextView) findViewById(R.id.network_scan_activity_progress_bar_text);
		
		pbText.setText("Scanning your network for devices ...");
		pb.setMax(pbEnd);
		pb.setProgress(0);
	    pb.setVisibility(View.VISIBLE);
		
		HostEnumerator hostEnumerator = new HostEnumerator(NetInfo.network_start, NetInfo.network_end, NetInfo.gatewayIp);
		hostEnumerator.addHostSearchProgressListener(_fragment);
		hostEnumerator.addHostSearchProgressListener(this);
		hostEnumerator.addHostSearchCompleteListener(this);
	    hostEnumerator.execute();
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		if(item.getItemId() == android.R.id.home)
		{
			GoToMainActivity();
	        return true;
		}
		
		return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onHostSearchProgress(Host host) 
	{
		ProgressBar pb = (ProgressBar) findViewById(R.id.network_scan_activity_progress_bar);
		pb.incrementProgressBy(1);
		
	}
	
	@Override
	public void onSearchComplete() 
	{
		ProgressBar pb = (ProgressBar) findViewById(R.id.network_scan_activity_progress_bar);
		TextView pbText = (TextView) findViewById(R.id.network_scan_activity_progress_bar_text);
		pbText.setText("Network Scan complete");
		pb.setVisibility(View.GONE);
	}

	
	private void GoToMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
	}
	
	
	
	
}











