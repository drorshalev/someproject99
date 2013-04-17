package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.MainActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.Serialiser;

public class NetworkScanListClickListener extends ListClickListener
{

	public NetworkScanListClickListener(SherlockFragmentActivity activity) 
	{
		super(activity, R.menu.scan_device_list_context_menu);
	}

	@Override
	public boolean actionItemClicked(ActionMode mode, MenuItem item) 
	{
		if(item.getItemId() == R.id.add_host)
		{
			List<View> vl = new ArrayList<View>(_selectedItems);
			List<Device> hl = new ArrayList<Device>();
			for(View v : vl){
				hl.add((Device)v.getTag());
			}
			
			Serialiser.AddHosts(hl, _activity);
        	mode.finish();
        	
        	Intent intent = new Intent(this._activity, MainActivity.class);
	        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        intent.putExtra("RefreshDeviceList",true);
	        _activity.startActivity(intent);
        	
            return false;
		}
		
		return true;
	}

}
