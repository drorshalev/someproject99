package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.ibus.autowol.backend.Serialiser;

public class DevicesListFragment extends SherlockFragment implements OnHostSearchProgressListener, PromptNetworkScanDialog.PromptNetworkScanDialogListener
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
		    	resetHostList();
		    /*}
		}*/
	}
	
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        listClickListener = new DeviceListClickListener((SherlockFragmentActivity)getActivity());
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
        _adapter = new HostListAdapter(getActivity(), R.id.ip_address, new ArrayList<Host>());
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(listClickListener); 
		listView.setAdapter(_adapter);
		
		//resetHostList();
		
		if(_adapter.GetItems().size() <= 0)
			PromptNetworkScan();
	}
	
	
	private void PromptNetworkScan()
    {
    	PromptNetworkScanDialog dlg = new PromptNetworkScanDialog();
    	dlg.setCancelable(false);
    	dlg.onAttach(this);
    	dlg.show(((SherlockFragmentActivity)getActivity()).getSupportFragmentManager(), "PromptNetworkScanDialog");
    }

	
	@Override
	public void onPromptNetworkScanPositiveClick(DialogInterface dialog) 
	{
		GoToNetworkScanActivity();
	}
	
	@Override
	public void onPromptNetworkScanNegativeClick(DialogInterface dialog) 
	{
	}
	
	public void GoToNetworkScanActivity()
    {
    	Intent intent = new Intent(getActivity(), NetworkScanActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
    }


	public void ClearList()
	{
		_adapter.clear();
		_adapter.notifyDataSetChanged();
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
	
	public void resetHostList()
	{
		HashSet<Host> savedDevices = Serialiser.GetHosts((SherlockFragmentActivity)getActivity());
		if(savedDevices != null)
		{
			_adapter.clear();
			_adapter.addAll(savedDevices);
			_adapter.notifyDataSetChanged();
		}
	}
	

}















