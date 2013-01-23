package com.ibus.autowol.ui;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.IpAddress;
import com.ibus.autowol.backend.MacAddress;
import com.ibus.autowol.backend.Host.HostType;

public class ActionBarNavigationListener implements ActionBar.OnNavigationListener
{
	SherlockFragmentActivity _activity;

	public ActionBarNavigationListener(SherlockFragmentActivity activity)
	{
		_activity = activity;
		
	}
	
	@Override
	public boolean onNavigationItemSelected(int position, long itemId) 
	{
		return DisplayDevicesFragment();
	}
	
	
	private boolean DisplayDevicesFragment()
	{
		 Host h = new Host(new IpAddress());
		 h.setHostType(HostType.PC);
		 h.setMacAddress(new MacAddress());
		 ArrayList<Host> l = new ArrayList<Host>();
		 l.add(h);
		
		
		DevicesListFragment mFragment = (DevicesListFragment)SherlockFragment.instantiate(_activity, DevicesListFragment.class.getName()); 
		mFragment.listClickListener = new DeviceListClickListener(_activity);
		mFragment.devices = l;  
		
		
		FragmentTransaction ft = _activity.getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, mFragment);
		ft.commit();
		
		return true;  
	}

}
