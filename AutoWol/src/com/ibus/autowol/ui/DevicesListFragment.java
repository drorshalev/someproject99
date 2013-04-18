package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Database;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.HostListAdapter;
import com.ibus.autowol.backend.Network;
import com.ibus.autowol.backend.Router;

public class DevicesListFragment extends SherlockFragment implements OnHostSearchProgressListener, OnHostSearchCompleteListener
{
	HostListAdapter _adapter;
	private ListClickListener listClickListener;
	boolean isCreating = false;
	Database database;
	private int currentNetworkRouterId;
	
	public DevicesListFragment()
	{
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		database = new Database(getActivity());
		super.onCreate(savedInstanceState);
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
		/*Spinner spiner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
        
		List<NetworkListItem> ar = new ArrayList<NetworkListItem>();
		ar.add(new NetworkListItem(Network.getRouter().getSsid()));
		
		spiner.setAdapter(new NetworkSpinnerAdapter(ar, getActivity().getLayoutInflater()));*/
		
		
		
		//create host list 
        _adapter = new HostListAdapter(getActivity(), R.id.host_list_item_ip_address, new ArrayList<Device>());
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(listClickListener); 
		listView.setAdapter(_adapter);
		
		resetHostList();
	}
	

	@Override
	public void onResume() 
	{
		super.onResume();
		
	} 
	
	
	
	
	public void resetHostList()
	{
		database.open();
		
		//get or create router 
		Router r = database.getRouterForMac(Network.getRouter().getMacAddress());
		if(r ==null)
			currentNetworkRouterId = database.saveRouter(Network.getRouter());
		else
			currentNetworkRouterId = r.getPrimaryKey();
		
		//get all routers into our spinner
		Spinner spiner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
        
		List<Router> rl = database.getAllRouters();
		spiner.setAdapter(new NetworkSpinnerAdapter(rl, getActivity().getLayoutInflater()));
		
		database.close();
		
		//select the router of our current network
		int pos = _adapter.GetPositionForMac(Network.getRouter().getMacAddress());
		if(pos != -1)
			spiner.setSelection(pos);
		
		//reset the devices list for our network
		_adapter.clear();
		
		HostEnumerator he = new HostEnumerator();
		he.addHostSearchProgressListener(this);
		he.addHostSearchCompleteListener(this);
		he.execute();		
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
		/*for(Device d : _adapter.GetItems())
		{
			database.	
		}*/
		
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















