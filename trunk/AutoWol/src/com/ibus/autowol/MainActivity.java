package com.ibus.autowol;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.ui.ActivityListItem;
import com.ibus.autowol.ui.DevicesListFragment;
import com.ibus.autowol.ui.NavigationSpinnerAdapter;
import com.ibus.autowol.ui.OnScanStartListener;

public class MainActivity extends SherlockFragmentActivity 
{	
	private static final String TAG = "MainActivity";
	private ActionBarNavigationListener _actionBarNavigationListener;
	List<OnScanStartListener> _scanStartListeners; 
	

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitialiseActionBar();
        _scanStartListeners = new ArrayList<OnScanStartListener>();
        
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	getSupportMenuInflater().inflate(R.menu.activity_main, menu);
    	
    	//MenuItem scan = (MenuItem)menu.findItem(R.id.activity_main_scan);
    	
    	//scan.setActionView(R.layout.progress_indicator);
    	//scan.setActionView(null);
    	
    	//scan.collapseActionView();
    	
    	//scan.setActionView(null);
    	
    	//scan.expandActionView();
    	
    	//scan.collapseActionView();
    	


    	
        return true; 
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            /*case R.id.add_host:
            	GoToNetworkScanActivity(); */ 
            case R.id.activity_main_scan:
            	startScan();   	
        }
        
        return true;
    }    
   
    private void startScan()
    {
    	for (OnScanStartListener listener : _scanStartListeners) 
		{
			listener.onScanStart();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	@Override
	protected void onNewIntent(Intent intent) 
	{
	    super.onNewIntent(intent);
	    intent.getStringExtra("DATA");
	}
	
	
	
	public void addScanStartListener(OnScanStartListener listener) {
    	_scanStartListeners.add(listener);
    }
    
    private void InitialiseActionBar()
    {
    	List<ActivityListItem> ar = new ArrayList<ActivityListItem>();
        ar.add(new ActivityListItem("Devices", "Devices"));
        ar.add(new ActivityListItem("Rules", "Rules"));
        ar.add(new ActivityListItem("Settings", "Settings"));
    	
        _actionBarNavigationListener = new ActionBarNavigationListener();
        
    	ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(new NavigationSpinnerAdapter(ar, this), _actionBarNavigationListener);
    }
    
   /* public void GoToNetworkScanActivity()
    {
    	Intent intent = new Intent(this, NetworkScanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/
    
    
	 @Override        
	 public void onSaveInstanceState(Bundle SavedInstanceState) {
		 super.onSaveInstanceState(SavedInstanceState);     
		 Log.i(TAG, "Saving instance state");
	 }
	 
	 @Override    
	 public void onRestoreInstanceState(Bundle savedInstanceState) {
		 super.onRestoreInstanceState(savedInstanceState);
		 Log.i(TAG, "Restoring instance state");
	 } 
	
    
	

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Navigation listener //////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public class ActionBarNavigationListener implements ActionBar.OnNavigationListener
	{
		DevicesListFragment _devicesListFragment;

		public ActionBarNavigationListener()
		{
		}
		
		@Override
		public boolean onNavigationItemSelected(int position, long itemId) 
		{
			return DisplayDevicesFragment();
		}
		
		
		private boolean DisplayDevicesFragment()
		{
			DevicesListFragment devicesListFragment = getDevicesListFragment();
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, devicesListFragment);
			
			ft.commit();
			
			return true;  
		}

		public DevicesListFragment getDevicesListFragment()
		{
			if(_devicesListFragment == null)
			{
				_devicesListFragment = (DevicesListFragment)SherlockFragment.instantiate(MainActivity.this, DevicesListFragment.class.getName()); 
				addScanStartListener(_devicesListFragment);
			}
			
			return _devicesListFragment;
		}
			    
	}
	
	
}
    


















/*@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        return false;
        
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HostScanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
        
    }*/

    
    

    /*@Override
     * public void onSaveInstanceState(Bundle outState) {
    	//first saving my state, so the bundle wont be empty.
    	//http://code.google.com/p/android/issues/detail?id=19917
    	outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    	super.onSaveInstanceState(outState);
    }*/
   


    

    
    //helper method
   /* private void enableEmbeddedTabs(Object actionBar) {
        try {
            Method setHasEmbeddedTabsMethod = actionBar.getClass().getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.invoke(actionBar, true);
        } catch (Exception e) {
            Log.e(TAG, "Error marking actionbar embedded", e);
        }
    }*/
    

    
    
    
    /*//ListView listView = getListView();
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            // Here you can do something when items are selected/de-selected,
            // such as update the title in the CAB
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // Respond to clicks on the actions in the CAB
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    deleteSelectedItems();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate the menu for the CAB
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // Here you can make any necessary updates to the activity when
            // the CAB is removed. By default, selected items are deselected/unchecked.
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // Here you can perform updates to the CAB due to
            // an invalidate() request
            return false;
        }
    });*/
    
    
    
    
    
    
    /* ******************************************************************* */
    
  //pre-ICS
  /*if (actionBar instanceof ActionBarImpl) {
      enableEmbeddedTabs(actionBar);*/
   
  //ICS and forward
    /*} else if (actionBar instanceof ActionBarWrapper) {
      try {
          Field actionBarField = actionBar.getClass().getDeclaredField("mActionBar");
          actionBarField.setAccessible(true);
          enableEmbeddedTabs(actionBarField.get(actionBar));
      } catch (Exception e) {
          Log.e(TAG, "Error enabling embedded tabs", e);
      }
  }
   */
  
 /* ******************************************************************* */
    
 /* try {
	  
      Field actionBarField = actionBar.getClass().getDeclaredField("mActionBar");
      actionBarField.setAccessible(true);
      enableEmbeddedTabs(actionBarField.get(actionBar));
  } catch (Exception e) {
      Log.e(TAG, "Error enabling embedded tabs", e);
  }*/
    
    
    
    /* ******************************************************************* */   
    
    
/*        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    //actionBar.setDisplayShowTitleEnabled(false);
    //actionBar.setDisplayShowHomeEnabled(false);

    Tab tab = actionBar.newTab()
            .setText("One")
            .setTabListener(
            		new TabListener<HostsFragment>(this, "one", HostsFragment.class));
    actionBar.addTab(tab);
    
    tab = actionBar.newTab()
            .setText("Two")
            .setTabListener(
            		new TabListener<HostsFragment>(this, "two", HostsFragment.class));
    actionBar.addTab(tab);*/
    
    
    /* ******************************************************************* */   
    
    
    //ActionBar actionBar = getActionBar();
    //actionBar.setDisplayHomeAsUpEnabled(true);
    
    
    
    
    //net = new NetInfo(getApplicationContext());
    //net.getIp();
   

    
   








