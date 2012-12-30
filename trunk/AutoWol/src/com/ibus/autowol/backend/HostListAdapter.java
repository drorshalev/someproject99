package com.ibus.autowol.backend;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ibus.autowol.R;

public class HostListAdapter extends ArrayAdapter<Host> 
{
	private ArrayList<Host> objects;

	public HostListAdapter(Context context, int textViewResourceId, ArrayList<Host> objects) 
	{
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.host_list_item, null);
		}

		Host host = objects.get(position); 
		
		if (host != null) 
		{
			TextView ip = (TextView) v.findViewById(R.id.ip_address);
			TextView mac = (TextView) v.findViewById(R.id.mac_address);

			ip.setText(host.getIpAddress().getAddress());
			mac.setText(host.getMacAddress().getAddress());
		}

		return v;
	}

}