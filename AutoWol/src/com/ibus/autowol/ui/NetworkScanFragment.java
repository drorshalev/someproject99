package com.ibus.autowol.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.HostListAdapter;

public class NetworkScanFragment extends SherlockFragment implements OnHostSearchProgressListener
{
	HostListAdapter _adapter;
	private ListClickListener listClickListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        listClickListener = new NetworkScanListClickListener((SherlockFragmentActivity)getActivity());
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
        _adapter = new HostListAdapter(getActivity(), R.id.host_list_item_ip_address, new ArrayList<Host>());
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(listClickListener); 
		listView.setAdapter(_adapter); 
	}
	

	@Override
	public void onHostSearchProgress(Host host) 
	{
		if(host != null)
		{
			_adapter.add(host);
			_adapter.notifyDataSetChanged();
		}
	}

}















