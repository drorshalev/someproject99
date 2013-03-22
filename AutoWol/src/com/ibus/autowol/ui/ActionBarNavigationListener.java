package com.ibus.autowol.ui;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ActionBarNavigationListener implements ActionBar.OnNavigationListener
{
	SherlockFragmentActivity _activity;
	DevicesListFragment _devicesListFragment;
	

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
		DevicesListFragment devicesListFragment = getDevicesListFragment();
		
		FragmentTransaction ft = _activity.getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, devicesListFragment);
		ft.commit();
		
		return true;  
	}
	

	public DevicesListFragment getDevicesListFragment()
	{
		if(_devicesListFragment == null)
		{
			_devicesListFragment = (DevicesListFragment)SherlockFragment.instantiate(_activity, DevicesListFragment.class.getName()); 
		}
		return _devicesListFragment;
	}
	

	
	
	    
		    
}













