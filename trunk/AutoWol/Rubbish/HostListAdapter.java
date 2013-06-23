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
	private List<Device> _devices;
	private List<View> _views = new ArrayList<View>();

	public HostListAdapter(Context context, int textViewResourceId, List<Device> objects) 
	{
		super(context, textViewResourceId, objects);
		this._devices = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.host_list_item, null);
		}

		Device host = _devices.get(position); 
		
		if (host != null) 
		{
			ImageView img = (ImageView) v.findViewById(R.id.host_list_item_device_image);
			TextView ip = (TextView) v.findViewById(R.id.host_list_item_ip_address);
			TextView mac = (TextView) v.findViewById(R.id.host_list_item_mac_address);
			TextView pcName = (TextView) v.findViewById(R.id.host_list_item_pc_name);

			ip.setText(host.getIpAddress());
			mac.setText(host.getMacAddress());
			pcName.setText(host.getName());
			
			//set default style to down
			img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pc_dissabled));
			ip.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
			mac.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
			pcName.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
			
			_views.add(v);
			
			host.setLive(false);
			v.setTag(host);
		}
		
		return v;
	}
	
	public List<Device> GetItems()
	{
		return _devices;
	}
	
	public int GetPositionForMac(String deviceMac)
	{
		Device d= GetDeviceForMac(deviceMac);
		if(d == null)
			return -1;
		
		return _devices.indexOf(d);
	}

	public Device GetDeviceForMac(String deviceMac)
	{
		for(Device d : _devices)
		{
			if(d.getMacAddress().equals(deviceMac))
				return d;
		}
		
		return null;
	}
	
	
	/*public void setLiveDevices(List<Device> deviceList)
	{
		int i = 1;
		int yh = i;
		
		for(Device d : _devices)
		{
			showDeviceOff(d);
		}

		for(Device d : deviceList)
		{
			showDeviceOn(d);
		}
	}*/
	
	
	/*public void showDeviceOn(Device device)
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
				
				dev.setLive(true);
			}
		}
		
	}
	
	
	public void showDeviceOff(Device device)
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
				
				img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pc_dissabled));
				ip.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
				mac.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
				pcName.setTextColor(getContext().getResources().getColor(R.color.dissabled_text));
				
				dev.setLive(false);
			}
		}
		
	}*/
	
	/*public List<Device> getLiveDevices()
	{
		List<Device> dl = new ArrayList<Device>();
		
		for(View v : _views)
		{
			Device dev = (Device)v.getTag();
			if(dev.isLive())
			{
				dl.add(dev);
			}
		}
		
		return dl;
	}*/
	
	
	
	
}






















