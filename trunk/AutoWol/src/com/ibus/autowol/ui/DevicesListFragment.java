package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Database;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.Factory;
import com.ibus.autowol.backend.HostListAdapter;
import com.ibus.autowol.backend.IHostEnumerator;
import com.ibus.autowol.backend.INetwork;
import com.ibus.autowol.backend.Router;
import com.ibus.autowol.backend.WolSender;

public class DevicesListFragment extends SherlockFragment implements OnScanProgressListener, OnScanCompleteListener, OnScanStartListener
{
	INetwork _network;
	IHostEnumerator _hostEnumerator;
	private final static String TAG = "AutoWol-DevicesListFragment";
	
	public DevicesListFragment()
	{
		_network = Factory.getNetwork();
		_hostEnumerator = Factory.getHostEnumerator();
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
		Log.i(TAG, "DevicesListFragment.onActivityCreated is executing");
		
		super.onActivityCreated(savedInstanceState);
		
		//create device list
		HostListAdapter deviceListadapter =new HostListAdapter(getActivity(), R.id.host_list_item_ip_address, new ArrayList<Device>());
		
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(new DeviceListClickListener()); 
		listView.setAdapter(deviceListadapter);
		
		//create network spinner
		Spinner netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
		netorkSpinner.setOnItemSelectedListener(new NetorkSelectedListener());
		
		//refresh network info
		_network.refresh(getActivity());
		
		//get or create router
		Database database = new Database(getActivity());
		database.open();
		
		Router router = database.getRouterForBssid(_network.getRouter().getBssid());
		if(router ==null)
		{
			database.saveRouter(_network.getRouter());
			Log.i(TAG, "This network is new.  New router saved to db.");
		}
		else
		{
			Log.i(TAG, "This network is known.  Existing router retrieved from db.");
		}
		
		//get all routers into our spinner
		List<Router> rl = database.getAllRouters();
		NetworkSpinnerAdapter ntwkAdapter =new NetworkSpinnerAdapter(rl, getActivity().getLayoutInflater()); 
		netorkSpinner.setAdapter(ntwkAdapter);
		
		//Select the router of our current network. Note the NetorkSelectedListener will fire regardless of 
		//whether we select anything 
		int pos = ntwkAdapter.GetPositionForBssid(_network.getRouter().getBssid());
		if(pos != -1)
		{
			netorkSpinner.setSelection(pos);
			Log.i(TAG, "router found in network spinner and selected");
		}
		else
			Log.i(TAG, "router was not found in our netowork spinner. SOMETHING HAS GONE WRONG.");
		
		//just check that we are in the apporpriate state
		if(deviceListadapter.GetItems().size() >0)
			Log.i(TAG, "adding devices to a non empty device list. SOMETHING HAS GONE WRONG.");
		
		database.close();
	}
	

	@Override
	public void onResume() 
	{
		super.onResume();
	} 
	
	@Override
	public void onScanStart() 
	{
		_network.refresh(getActivity());
		ScanNetwork();
	}
	
	@Override
	public void onScanProgress(Device host, int progress) 
	{
		ProgressBar pb = (ProgressBar) getActivity().findViewById(R.id.host_fragment_progress_bar);
		pb.incrementProgressBy(progress);
		
		if(host != null)
		{
			ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
			HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
			
			Device d = adapter.GetDeviceForMac(host.getMacAddress());
			if(d != null)
				d.copyFromScannedDevice(host);
			else
				adapter.add(host);
			
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onScanComplete() 
	{
		Database database = new Database(getActivity());
		database.open();
		
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
		
		Router r = database.getRouterForBssid(_network.getRouter().getBssid());
		
		database.deleteDevicesForRoputer(r.getPrimaryKey());
		database.saveDevicesForRoputer(adapter.GetItems(), r.getPrimaryKey());
		
		database.close();
		
		ProgressBar pb = (ProgressBar) getActivity().findViewById(R.id.host_fragment_progress_bar);
		pb.setVisibility(View.GONE);
		
		LinearLayout pl = (LinearLayout) getActivity().findViewById(R.id.host_fragment_progress_bar_placeholder);
	    pl.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		_hostEnumerator.cancel();
	}
	
	private void ScanNetwork()
	{
		ProgressBar pb = (ProgressBar) getActivity().findViewById(R.id.host_fragment_progress_bar);
		pb.setMax(255);
		pb.setProgress(0);
	    pb.setVisibility(View.VISIBLE);
		
	    LinearLayout pl = (LinearLayout) getActivity().findViewById(R.id.host_fragment_progress_bar_placeholder);
	    pl.setVisibility(View.GONE);
	    
	    //change the network spinner to select the network we are on
	    Spinner netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
	    NetworkSpinnerAdapter ntwkAdapter = (NetworkSpinnerAdapter)netorkSpinner.getAdapter();

		int pos = ntwkAdapter.GetPositionForBssid(_network.getRouter().getBssid());
		if(pos != -1)
		{
			netorkSpinner.setSelection(pos);
			Log.i(TAG, "router found in network spinner and selected");
		}
		else
			Log.i(TAG, "router was not found in our netowork spinner. SOMETHING HAS GONE WRONG.");
	    
		_hostEnumerator.scan(_network, this, this);
	}
	
	
	
	 @Override        
	 public void onSaveInstanceState(Bundle SavedInstanceState) {
		 super.onSaveInstanceState(SavedInstanceState);
		 Log.i(TAG, "Saving instance state");
	 }
	  
	

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Event Listeners //////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	//
	//Devices list item clicked
	//
	public class DeviceListClickListener extends ListClickListener
	{
		public DeviceListClickListener() 
		{
			super((SherlockFragmentActivity)getActivity(), R.menu.device_list_context_menu);
		}

		@Override
		public boolean actionItemClicked(ActionMode mode, MenuItem item) 
		{
			if(item.getItemId() == R.id.device_list_context_menu_delete)
			{
	            	mode.finish();
	                return false;
			}
			else if(item.getItemId() == R.id.device_list_context_menu_wake)
			{
				List<Device> dl = getSelectedItems();
				Device[] da = dl.toArray(new Device[dl.size()]);
				
				WolSender sender = new WolSender();
				sender.execute(da);
			}
			
			return true;
		}
	}
	
	//
	//Network Spinner selection changed
	//
	public class NetorkSelectedListener implements OnItemSelectedListener
	{

		//get or create devices on our network.  
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			//after memory clear arg1 is null. Looks like the activiy starts and this is called as part of initialisation and when it is called 
			//arg1 is null. then straight after destroy is called and the activity is started again normally.   
			if(arg1 == null)
				return;
			
			Database database = new Database(getActivity());
			database.open();
			String routerBssid = (String)arg1.getTag();
			
			Router r = database.getRouterForBssid(routerBssid);
			List<Device> devices = database.getDevicesForRouter(r.getPrimaryKey());
			
			Log.i(TAG, String.format("%d devices found for router with bssid: %s", devices.size(), _network.getRouter().getBssid()));
			
			//add devices to our device list or scan the network if our device list is empty
			ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
			HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
			adapter.clear();
			
			if(devices.size() > 0)
			{
				adapter.addAll(devices);
				adapter.notifyDataSetChanged();
			}
			else
			{
				_network.refresh(getActivity());
				ScanNetwork();
			}
			
			database.close();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
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












