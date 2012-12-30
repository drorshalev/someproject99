package com.ibus.autowol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.backend.NetInfo;
import com.ibus.autowol.ui.ActionBarNavigationListener;
import com.ibus.autowol.ui.ActivityListItem;
import com.ibus.autowol.ui.HostsFragment;
import com.ibus.autowol.ui.NavigationSpinnerAdapter;
//import android.app.ActionBar;
//import android.view.Menu;
//import android.view.MenuItem;
//import com.actionbarsherlock.view.MenuInflater;


public class MainActivity extends SherlockFragmentActivity 
{	
	private static final String TAG = "MainActivity";
	//protected NetInfo net = null;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetInfo.refresh(this);
        
        InitialiseActionBar();
    }
    
    
    private void InitialiseActionBar()
    {
    	List<ActivityListItem> ar = new ArrayList<ActivityListItem>();
        ar.add(new ActivityListItem("Hosts", "Hosts"));
        ar.add(new ActivityListItem("Policies", "Policies"));
        ar.add(new ActivityListItem("Configuration", "Configuration"));
    	
    	ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(new NavigationSpinnerAdapter(ar, this), new ActionBarNavigationListener(this));
    }
    
    
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	//first saving my state, so the bundle wont be empty.
    	//http://code.google.com/p/android/issues/detail?id=19917
    	outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    	super.onSaveInstanceState(outState);
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    

    
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
   
}
    
   








