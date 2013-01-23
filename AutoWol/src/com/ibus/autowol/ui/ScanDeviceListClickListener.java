package com.ibus.autowol.ui;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.MainActivity;
import com.ibus.autowol.R;

public class ScanDeviceListClickListener extends ListClickListener 
{

	public ScanDeviceListClickListener(SherlockFragmentActivity activity) 
	{
		super(activity, R.menu.scan_device_list_context_menu);
	}

	@Override
	public boolean actionItemClicked(ActionMode mode, MenuItem item) 
	{
		if(item.getItemId() == R.id.add_host)
		{
            	mode.finish();
            	
            	Intent intent = new Intent(this._activity, MainActivity.class);
    	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        _activity.startActivity(intent);
            	
                return false;
		}
		
		return true;
	}

}
