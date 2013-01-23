package com.ibus.autowol.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.MainActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.NetInfo;
import com.ibus.autowol.backend.Serialiser;

public class NetworkScanActivity extends SherlockFragmentActivity implements OnHostSearchCompleteListener
{
	DevicesListFragment _fragment;
	
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
		_fragment = (DevicesListFragment)SherlockFragment.instantiate(this, DevicesListFragment.class.getName()); 
		_fragment.listClickListener = new ScanDeviceListClickListener(this);
		  
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, _fragment);
		ft.commit();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();

		HostEnumerator hostEnumerator = new HostEnumerator(NetInfo.network_start, NetInfo.network_end, NetInfo.gatewayIp);
		hostEnumerator.addHostFoundListener(_fragment);
		hostEnumerator.addHostSearchCompleteListener(this);
	    hostEnumerator.execute();
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		if(item.getItemId() == android.R.id.home)
		{
			Intent intent = new Intent(this, MainActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
	        
	        return true;
		}
		
		return super.onOptionsItemSelected(item);
    }

	@Override
	public void onSearchComplete() 
	{
		/*List<Host> l = new ArrayList<Host>();
        
        Host h = new Host(new IpAddress("10.0.0.1"));
        h.setDeviceTypeImage(1);
        h.setHostType(HostType.PC);
        h.setMacAddress(new MacAddress());
        l.add(h);
        
        Host h2 = new Host(new IpAddress("10.0.0.2"));
        h2.setDeviceTypeImage(2);
        h2.setHostType(HostType.Gateway);
        h2.setMacAddress(new MacAddress());
        l.add(h2);*/
        
       /* Serialiser.Serialise(l, "devices.bin", this);
        
        List<Host> l2 = (List<Host>)Serialiser.Deserialise("devices.bin", this);*/
        
		List<Host> l = _fragment.devices;
	}
	
	
	
	
	
}











