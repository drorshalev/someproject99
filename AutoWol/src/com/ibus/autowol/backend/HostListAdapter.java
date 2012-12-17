package com.ibus.autowol.backend;

import java.util.ArrayList;

import com.ibus.autowol.R;
import com.ibus.autowol.R.id;
import com.ibus.autowol.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HostListAdapter extends ArrayAdapter<Host> 
{

	// declaring our ArrayList of items
	private ArrayList<Host> objects;

	/* here we must override the constructor for ArrayAdapter
	* the only variable we care about now is ArrayList<Item> objects,
	* because it is the list of objects we want to display.
	*/
	public HostListAdapter(Context context, int textViewResourceId, ArrayList<Host> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.host_list_item, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Host host = objects.get(position);

		if (host != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.
			TextView ip = (TextView) v.findViewById(R.id.ip_address);
			TextView mac = (TextView) v.findViewById(R.id.mac_address);

			ip.setText(host.getIpAddress().getAddress());
			mac.setText(host.getMacAddress().getAddress());
		}

		// the view must be returned to our activity
		return v;
	}

}