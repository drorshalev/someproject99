package com.ibus.autowol.ui;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.R;

public class DeviceListClickListener extends ListClickListener
{

	public DeviceListClickListener(SherlockFragmentActivity activity) {
		super(activity, R.menu.device_list_context_menu);
	}

	@Override
	public boolean actionItemClicked(ActionMode mode, MenuItem item) 
	{
		if(item.getItemId() == R.id.delete_host)
		{
            	mode.finish();
                return false;
		}
		
		return true;
	}

}
