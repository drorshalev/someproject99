package com.ibus.autowol.backend;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ibus.autowol.R;

public class HostListAdapter extends ArrayAdapter<Device> 
{
	private List<Device> _objects;

	public HostListAdapter(Context context, int textViewResourceId, List<Device> objects) 
	{
		super(context, textViewResourceId, objects);
		this._objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.host_list_item, null);
		}

		Device host = _objects.get(position); 
		
		if (host != null) 
		{
			TextView ip = (TextView) v.findViewById(R.id.host_list_item_ip_address);
			TextView mac = (TextView) v.findViewById(R.id.host_list_item_mac_address);
			TextView pcName = (TextView) v.findViewById(R.id.host_list_item_pc_name);

			ip.setText(host.getIpAddress());
			mac.setText(host.getMacAddress());
			pcName.setText(host.getName());
		}
		
		v.setTag(host);
		return v;
	}
	
	public List<Device> GetItems()
	{
		return _objects;
	}

}