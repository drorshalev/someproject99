package com.ibus.autowol.ui;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;

public class DeviceListClickListener extends ListClickListener
{

	public DeviceListClickListener(SherlockFragmentActivity activity) {
		super(activity, R.menu.device_list_context_menu);
	}

	@Override
	public boolean actionItemClicked(ActionMode mode, MenuItem item) 
	{
		if(item.getItemId() == R.id.device_list_context_menu_delete)
		{
            	mode.finish();
                return false;
		}
		else if(item.getItemId() == R.id.device_list_context_menu_wake)
		{
			List<Host> hosts = GetItems();
		}
			
		
		
		
		return true;
	}

}
