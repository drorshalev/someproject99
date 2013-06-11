package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.ibus.autowol.backend.IPinger;
import com.ibus.autowol.backend.ThreadResult;
import com.ibus.autowol.backend.Router;
import com.ibus.autowol.backend.WolSender;

public class DevicesListFragment extends SherlockFragment 
implements OnScanProgressListener, OnScanCompleteListener, OnScanStartListener, OnPingProgressListener, OnPingCompleteListener
{
	private final static String TAG = "AutoWol-DevicesListFragment";
	//List<Device> _liveDevices = new ArrayList<Device>();
	ProgressDialog _progressDialog;
	IHostEnumerator _hostEnumerator;
	IPinger _pinger;
	INetwork _network;
	Spinner _netorkSpinner;
	DeviceListView _deviceListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		//need to create network here because our activity context has not yet been created
		_network = Factory.getNetwork(getActivity());
		
		_hostEnumerator = Factory.getHostEnumerator();
		_hostEnumerator.addOnScanProgressListener(this);
		_hostEnumerator.addOnScanCompleteListener(this);
		
		_pinger = Factory.getPinger();
        _pinger.addOnPingCompleteListener(this);
        _pinger.addOnPingProgressListener(this);
		
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        return v; 
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		Log.i(TAG, "DevicesListFragment.onActivityCreated is executing");
		
		super.onActivityCreated(savedInstanceState);
		
		//create device list
		_deviceListView = (DeviceListView) getActivity().findViewById(R.id.host_list);
		_deviceListView.setOnItemClickListener(new DeviceListClickListener()); 
		
		//create network spinner
		_netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
		_netorkSpinner.setOnItemSelectedListener(new NetorkSelectedListener());
		
		Database database = new Database(getActivity());
		database.open();
		
		boolean isConnected =_network.isWifiNetworkConnected(getActivity());
		
		if(isConnected)
		{
			//update or create router
			Router router =_network.getRouter();
			database.saveRouter(router);
		
			//populate network spinner with all of our routers
			populateRouterSpinner(database);
			
			//Select the router of our current network. Note the NetorkSelectedListener will fire regardless of 
			//whether we select anything 
			selectRouter(router.getBssid());
				
			List<Device> dl = database.getDevicesForRouter(router.getBssid());
			if(dl.size() == 0)
			{
				//either this network has no devices or this is the first time we have run the app in this network
				//.... prompt user "you currently dont have any devices listed for the network, would you like to scan for devices now?"
				ScanNetwork();
			}
			else
			{
				//we have run the app on this network before. add the devices that we previously scanned to host list
				_deviceListView.setDevices(dl);
			}
		}
		else
		{
			//just populate network spinner with previously discovered routers / networks if we are not on a network
			populateRouterSpinner(database);
			
			//TODO: will get the first router?
			Router r = GetSelectedRouter();
			if(r != null)
			{
				List<Device> dl = database.getDevicesForRouter(r.getBssid());
				_deviceListView.setDevices(dl);
			}
			
			Toast.makeText(getActivity(), "Network scan aborted: you are not connected to a network", Toast.LENGTH_LONG).show();
		}
		
		database.close();
	}
	
	
	@Override
	public void onResume() 
	{
		//fragment is visible here
		super.onResume();
		
		//_network.refresh(getActivity());
		
		PingDevices();
	
		Log.i(TAG, "onResume");
	}

	//
	//Network Spinner selection changed
	//
	public class NetorkSelectedListener implements OnItemSelectedListener
	{
		private boolean _calledBefore = false;
		
		//get or create devices on our network.  
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			if(!_calledBefore){
				_calledBefore = true;
				return;
			}
			//after memory clear arg1 is null. Looks like the activiy starts and this is called as part of initialisation and when it is called 
			//arg1 is null. then straight after destroy is called and the activity is started again normally.   
			if(arg1 == null)
				return;
			
			String routerBssid = (String)arg1.getTag();
			
			Database database = new Database(getActivity());
			database.open();
			
			List<Device> devices = database.getDevicesForRouter(routerBssid);
			Log.i(TAG, String.format("%d devices found for router with bssid: %s", devices.size(), routerBssid));
			
			//add devices to our device list or scan the network if our device list is empty
			//DeviceListView listView = (DeviceListView) getActivity().findViewById(R.id.host_list);
			//HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
			
			//_pinger.stop();
			
			_deviceListView.setDevices(devices);
			
			/*if(routerBssid.equals(_network.getRouter().getBssid()))
				PingDevices();
			else
				_pinger.stop();*/
			
			
			/*if(devices.size() > 0)
			{
				//_network.refresh(getActivity());
				
				if(routerBssid.equals(_network.getRouter().getBssid()))
				{
					adapter.clear();
					adapter.addAll(devices);
					adapter.notifyDataSetChanged();
					adapter.setLiveDevices(_liveDevices);
					
					
					//PingDevices();
				}
				else
				{
					_liveDevices = adapter.getLiveDevices();
					adapter.clear();
					adapter.addAll(devices);
					adapter.notifyDataSetChanged();
					
					//_pinger.stop();
				}
			}
			else
			{
				_liveDevices = adapter.getLiveDevices();
				adapter.clear();
				adapter.notifyDataSetChanged();
				//_pinger.stop();
			}
			*/
			database.close();
		}
	
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	
	}
	
	
	@Override
	public void onScanStart() 
	{
		//only scan and refresh view if we are on a network
		//_network.refresh(getActivity());
		boolean isConnected =_network.isWifiNetworkConnected(getActivity());
		
		if(isConnected)
		{
			Database database = new Database(getActivity());
			database.open();

			//update or create router
			Router router = _network.getRouter();
			database.saveRouter(router);

			//populate network spinner with all of our routers again since the list might have changed
			populateRouterSpinner(database);
			
			//Select the router of our current network. Note the NetorkSelectedListener will fire regardless of 
			//whether we select anything 
			selectRouter(router.getBssid());
			
			database.close();
		
			//the NetorkSelectedListener should already have completed by now???
			ScanNetwork();
		}
		else
		{
			Toast.makeText(getActivity(), "Network scan aborted: you are not connected to a network", Toast.LENGTH_LONG).show();
		}
	}
	
	

	@Override
	public void onStop() 
	{
		//fragment is hidden here
		super.onStop();

		_pinger.stop();		
		Log.i(TAG, "onStop");
	}
		

	@Override
	public void onPingComplete(boolean success) 
	{
		Log.i(TAG, "Ping complete");
	}

	@Override
	public void onPingProgress(ThreadResult result) 
	{
		Log.i(TAG, "Ping progress called");
		
		//thread may call into this method after onDestroy is called!!!!!
		if(getActivity() == null){
			Log.i(TAG, "getActivity is null ");
			return;
		}
		
		/*DeviceListView listView = (DeviceListView) getActivity().findViewById(R.id.host_list);
		HostListAdapter adapter = (HostListAdapter)listView.getAdapter();*/
		
		if(result.success)
		{
			_deviceListView.showDeviceOn(result.device);
		}
		else
			_deviceListView.showDeviceOff(result.device);
		
		
		to do:
			merge  _deviceListView and HostListAdapter
			add logic to hostlist adapter so it shows as live or not when returning a View. live flag now included in device and set in showDeviceOn ...
			add methods to _deviceListView: setLiveDevices(), getLiveDevices() 
			
	}
	
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		_hostEnumerator.stop();
	}
	
	

	 @Override        
	 public void onSaveInstanceState(Bundle SavedInstanceState) 
	 {
		 super.onSaveInstanceState(SavedInstanceState);
		 Log.i(TAG, "Saving instance state");
	 }
	
	
	@Override
	public void onAttach (Activity activity)
	{
		super.onAttach(activity);
	}
	
	@Override
	public void onDetach ()
	{
		super.onDetach();
	}
	
	
	
	@Override
	public void onScanProgress(ThreadResult thread) 
	{
		if(thread.device != null)
		{
			DeviceListView listView = (DeviceListView) getActivity().findViewById(R.id.host_list);
			HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
			
			Device d = adapter.GetDeviceForMac(thread.device.getMacAddress());
			if(d != null)
				d.copyFromScannedDevice(thread.device);
			else
				adapter.add(thread.device);
			
			adapter.notifyDataSetChanged();
		}
		
	}
	
	
	@Override
	public void onScanComplete() 
	{
		Database database = new Database(getActivity());
		database.open();
		
		DeviceListView listView = (DeviceListView)getActivity().findViewById(R.id.host_list);
		HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
		
		Router r = database.getRouterForBssid(_network.getRouter().getBssid());
		
		//add or update all devices in our adapter to our db 
		database.saveDevicesForRouter(adapter.GetItems(), r.getPrimaryKey());
		
		database.close();
		
		_progressDialog.dismiss();
	}

	
	
	
	

	

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Utilities //////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void PingDevices()
	{
		boolean isConnected =_network.isWifiNetworkConnected(getActivity());
		if(!isConnected)
			return;
		
		Router r =  GetSelectedRouter();
		
		//ping displayed devices if they belong to the network we are in
		if(r.getBssid().equals(_network.getRouter().getBssid()))
		{
	        _pinger.start(GetDevices());
		}
	}
	
	
	private Router GetSelectedRouter()
	{
		Spinner netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
		return (Router)netorkSpinner.getSelectedItem();	
	}
	
	
	private List<Device> GetDevices()
	{
		DeviceListView listView = (DeviceListView) getActivity().findViewById(R.id.host_list);
		HostListAdapter adapter = (HostListAdapter)listView.getAdapter();
        return adapter.GetItems();
	}
	
	
	private void ScanNetwork()
	{
		_progressDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_DARK);
		_progressDialog.setTitle("Scanning network...");
		_progressDialog.setMessage("Please wait.");
		_progressDialog.setCancelable(false);
		_progressDialog.setIndeterminate(true);
		_progressDialog.show();
		
		_hostEnumerator.start(_network);
	}
	
	
	
	 private void populateRouterSpinner(Database database)
	 {
		 Spinner netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
		 
		 List<Router> rl = database.getAllRouters();
		 NetworkSpinnerAdapter ntwkAdapter =new NetworkSpinnerAdapter(rl, getActivity().getLayoutInflater()); 
		 netorkSpinner.setAdapter(ntwkAdapter);
	 }


	 private void selectRouter(String bssid)
	 { 
		 Spinner netorkSpinner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
		 NetworkSpinnerAdapter ntwkAdapter = (NetworkSpinnerAdapter)netorkSpinner.getAdapter();
		 
		 int pos = ntwkAdapter.GetPositionForBssid(bssid);
		 if(pos != -1)
		 {
			 netorkSpinner.setSelection(pos);
			 Log.i(TAG, "router found in network spinner and selected");
		 }
		 else
			 Log.i(TAG, "router was not found in our netowork spinner. SOMETHING HAS GONE WRONG.");
	 }
	

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












