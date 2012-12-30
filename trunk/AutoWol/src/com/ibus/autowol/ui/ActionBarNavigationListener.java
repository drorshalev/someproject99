package com.ibus.autowol.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

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
		  Fragment mFragment = SherlockFragment.instantiate(_activity, HostsFragment.class.getName()); 
		  
		  FragmentTransaction ft = _activity.getSupportFragmentManager().beginTransaction();
		  ft.replace(android.R.id.content, mFragment, Long.toString(itemId));
		  ft.commit();
		  
		  return true;  
	  }

}
