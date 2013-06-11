package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.List;

import com.ibus.autowol.R;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.HostListAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceListView extends ListView {
	HostListAdapter _deviceListadapter;

	public DeviceListView(Context context) {
		super(context);
		init();
	}

	// This example uses this method since being built from XML
	public DeviceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	// Build from XML layout
	public DeviceListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		setChoiceMode(DeviceListView.CHOICE_MODE_SINGLE);
		_deviceListadapter = new HostListAdapter(getContext(),
				R.id.host_list_item_ip_address, new ArrayList<Device>());
		setAdapter(_deviceListadapter);
	}

	public void setDevices(List<Device> devices) {
		_deviceListadapter.clear();
		_deviceListadapter.addAll(devices);
		_deviceListadapter.notifyDataSetChanged();
	}

	
	public void showDeviceOn(Device device) {
		for (int i = 0; i < this.getChildCount(); ++i) 
		{
			View v = this.getChildAt(i);
			Device dev = (Device) v.getTag();
			
			if (dev.getMacAddress().equals(device.getMacAddress())) 
			{
				setViewOn(v);
				dev.setLive(false);
			}
		}
	}

	public void showDeviceOff(Device device) 
	{
		for (int i = 0; i < this.getChildCount(); ++i) 
		{
			View v = this.getChildAt(i);
			Device dev = (Device) v.getTag();
			
			if (dev.getMacAddress().equals(device.getMacAddress())) 
			{
				setViewOff(v);
				dev.setLive(false);
			}
		}

	}

	// Utilities
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	private void setViewOff(View v) 
	{
		TextView ip = (TextView) v.findViewById(R.id.host_list_item_ip_address);
		TextView mac = (TextView) v
				.findViewById(R.id.host_list_item_mac_address);
		TextView pcName = (TextView) v
				.findViewById(R.id.host_list_item_pc_name);
		ImageView img = (ImageView) v
				.findViewById(R.id.host_list_item_device_image);

		img.setImageDrawable(getContext().getResources().getDrawable(
				R.drawable.ic_pc_dissabled));
		ip.setTextColor(getContext().getResources().getColor(
				R.color.dissabled_text));
		mac.setTextColor(getContext().getResources().getColor(
				R.color.dissabled_text));
		pcName.setTextColor(getContext().getResources().getColor(
				R.color.dissabled_text));
	}

	private void setViewOn(View v) 
	{
		TextView ip = (TextView) v.findViewById(R.id.host_list_item_ip_address);
		TextView mac = (TextView) v
				.findViewById(R.id.host_list_item_mac_address);
		TextView pcName = (TextView) v
				.findViewById(R.id.host_list_item_pc_name);
		ImageView img = (ImageView) v
				.findViewById(R.id.host_list_item_device_image);

		img.setImageDrawable(getContext().getResources().getDrawable(
				R.drawable.ic_pc));
		ip.setTextColor(getContext().getResources().getColor(R.color.text));
		mac.setTextColor(getContext().getResources().getColor(R.color.text));
		pcName.setTextColor(getContext().getResources().getColor(R.color.text));
	}

}
