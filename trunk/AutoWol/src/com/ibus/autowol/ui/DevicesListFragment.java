package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.HostListAdapter;
import com.ibus.autowol.backend.Network;
import com.ibus.autowol.backend.Serialiser;

public class DevicesListFragment extends SherlockFragment implements OnHostSearchProgressListener, OnHostSearchCompleteListener
{
	HostListAdapter _adapter;
	private ListClickListener listClickListener;
	boolean isCreating = false;
	
	public DevicesListFragment()
	{
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		
		/*Bundle extras = getActivity().getIntent().getExtras();
		
		if (extras != null) 
		{
		    boolean doRefresh = extras.getBoolean("RefreshDeviceList");
		    if(doRefresh)
		    {*/
		    	//resetHostList();
		    /*}
		}*/
	} 
	
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		listClickListener = new DeviceListClickListener((SherlockFragmentActivity)getActivity());
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		//create network spinner
		Spinner spiner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
        
		List<NetworkListItem> ar = new ArrayList<NetworkListItem>();
		ar.add(new NetworkListItem(Network.getNetworkName()));
		
		spiner.setAdapter(new NetworkSpinnerAdapter(ar, getActivity().getLayoutInflater()));
		
		//create host list 
        _adapter = new HostListAdapter(getActivity(), R.id.host_list_item_ip_address, new ArrayList<Device>());
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(listClickListener); 
		listView.setAdapter(_adapter);
		
		resetHostList();
		
		/*if(_adapter.GetItems().size() <= 0)
			PromptNetworkScan();*/
	}
	

	public void resetHostList()
	{
		//HashSet<Host> savedDevices = Serialiser.GetHosts((SherlockFragmentActivity)getActivity());
		_adapter.clear();
		
		HostEnumerator he = new HostEnumerator();
		he.addHostSearchProgressListener(this);
		he.addHostSearchCompleteListener(this);
		he.execute();
		
		/*if(devices != null)
		{
			_adapter.clear();
			_adapter.addAll(savedDevices);
			_adapter.notifyDataSetChanged();
		}*/
	}
	
	@Override
	public void onHostSearchProgress(Device host) 
	{
		if(host != null)
		{
			_adapter.add(host);
			_adapter.notifyDataSetChanged();
		}
	}
	
	
	@Override
	public void onSearchComplete() 
	{
		/*ProgressBar pb = (ProgressBar) findViewById(R.id.network_scan_activity_progress_bar);
		TextView pbText = (TextView) findViewById(R.id.network_scan_activity_progress_bar_text);
		pbText.setText("Network Scan complete");
		pb.setVisibility(View.GONE);*/
	}
	
	
	
	
	/*private void PromptNetworkScan()
    {
    	PromptNetworkScanDialog dlg = new PromptNetworkScanDialog();
    	dlg.setCancelable(false);
    	dlg.onAttach(this);
    	dlg.show(((SherlockFragmentActivity)getActivity()).getSupportFragmentManager(), "PromptNetworkScanDialog");
    }*/

	
	/*@Override
	public void onPromptNetworkScanPositiveClick(DialogInterface dialog) 
	{
		GoToNetworkScanActivity();
	}
	
	@Override
	public void onPromptNetworkScanNegativeClick(DialogInterface dialog) 
	{
	}*/
	
	/*public void GoToNetworkScanActivity()
    {
    	Intent intent = new Intent(getActivity(), NetworkScanActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
    }*/


	public void ClearList()
	{
		_adapter.clear();
		_adapter.notifyDataSetChanged();
	}


	
	
	

}















