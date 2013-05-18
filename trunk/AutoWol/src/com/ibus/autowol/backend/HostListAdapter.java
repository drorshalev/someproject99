package com.ibus.autowol.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibus.autowol.R;

public class HostListAdapter extends ArrayAdapter<Device> 
{
	private List<Device> _objects;
	private List<View> _views = new ArrayList<View>();

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
			
			_views.add(v);
		}
		
		v.setTag(host);
		
		
		return v;
	}
	
	public List<Device> GetItems()
	{
		return _objects;
	}
	
	public int GetPositionForMac(String deviceMac)
	{
		Device d= GetDeviceForMac(deviceMac);
		if(d == null)
			return -1;
		
		return _objects.indexOf(d);
	}

	public Device GetDeviceForMac(String deviceMac)
	{
		for(Device d : _objects)
		{
			if(d.getMacAddress().equals(deviceMac))
				return d;
		}
		
		return null;
	}
	
	public void enableView(Device device)
	{
		for(View v : _views)
		{
			Device dev = (Device)v.getTag();
			if(dev.getMacAddress().equals(device.getMacAddress()))
			{
				TextView ip = (TextView) v.findViewById(R.id.host_list_item_ip_address);
				TextView mac = (TextView) v.findViewById(R.id.host_list_item_mac_address);
				TextView pcName = (TextView) v.findViewById(R.id.host_list_item_pc_name);
				ImageView img = (ImageView) v.findViewById(R.id.host_list_item_device_image);
				
				img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pc));
				ip.setTextColor(getContext().getResources().getColor(R.color.text));
				mac.setTextColor(getContext().getResources().getColor(R.color.text));
				pcName.setTextColor(getContext().getResources().getColor(R.color.text));
			}
		}
		
	}
	
}



