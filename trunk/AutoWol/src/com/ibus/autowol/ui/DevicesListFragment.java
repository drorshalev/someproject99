package com.ibus.autowol.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.HostListAdapter;

public class DevicesListFragment extends SherlockFragment implements OnHostFoundListener
{
	HostListAdapter _adapter;
	public ListClickListener listClickListener;
	public ArrayList<Host> devices;
	
	public DevicesListFragment()
	{
		devices = new ArrayList<Host>();
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
        _adapter = new HostListAdapter(getActivity(), R.id.ip_address, devices);
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(listClickListener); 
		listView.setAdapter(_adapter);
	}


	@Override
	public void onHostFound(Host host) 
	{
		_adapter.add(host);
		_adapter.notifyDataSetChanged();
	}
	
	
	
	public void ClearList()
	{
		_adapter.clear();
		_adapter.notifyDataSetChanged();
	}


	
	
	
}















