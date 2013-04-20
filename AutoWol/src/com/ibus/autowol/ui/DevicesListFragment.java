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
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Database;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.HostListAdapter;
import com.ibus.autowol.backend.Network;
import com.ibus.autowol.backend.Router;
import com.ibus.autowol.backend.WolSender;

public class DevicesListFragment extends SherlockFragment implements OnScanProgressListener, OnScanCompleteListener, OnScanStartListener
{
	HostListAdapter _deviceListadapter;
	private ListClickListener _listClickListener;
	private Network _network;

	public void setNetwork(Network network) {
		_network = network;
	}

	
	
	public DevicesListFragment()
	{
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		_listClickListener = new DeviceListClickListener();
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		//create host list 
        _deviceListadapter = new HostListAdapter(getActivity(), R.id.host_list_item_ip_address, new ArrayList<Device>());
		 
		ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(_listClickListener); 
		listView.setAdapter(_deviceListadapter);
		
		_network.refresh(getActivity());
		initialiseNetworkList();
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
		startScan();
	}
	
	@Override
	public void onScanProgress(Device host) 
	{
		if(host != null)
		{
			Device d = _deviceListadapter.GetDeviceForMac(host.getMacAddress());
			if(d != null)
				d.copyFromScannedDevice(host);
			else
				_deviceListadapter.add(host);
			
			_deviceListadapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onScanComplete() 
	{
		Database database = new Database(getActivity());
		database.open();
		
		Router r = database.getRouterForMac(_network.getRouter().getMacAddress());
		
		database.deleteDevicesForRoputer(r.getPrimaryKey());
		database.saveDevicesForRoputer(_deviceListadapter.GetItems(), r.getPrimaryKey());
		
		database.close();
	}

	
	public void initialiseNetworkList()
	{
		Database database = new Database(getActivity());
		database.open();
		
		//get or create router 
		Router r = database.getRouterForMac(_network.getRouter().getMacAddress());
		int routerPk;
		if(r ==null)
			routerPk = database.saveRouter(_network.getRouter());
		else
			routerPk = r.getPrimaryKey();
		
		//get all routers into our spinner
		Spinner spiner = (Spinner) getActivity().findViewById(R.id.host_fragment_networks);
        
		List<Router> rl = database.getAllRouters();
		NetworkSpinnerAdapter ntwkAdapter =new NetworkSpinnerAdapter(rl, getActivity().getLayoutInflater()); 
		spiner.setAdapter(ntwkAdapter);
		
		//select the router of our current network
		int pos = ntwkAdapter.GetPositionForMac(_network.getRouter().getMacAddress());
		if(pos != -1)
			spiner.setSelection(pos);
		
		//get or create devices on our network.  
		List<Device> devices = database.getDevicesForRouter(routerPk);
		if(devices.size() > 0)
		{
			_deviceListadapter.addAll(devices);
			_deviceListadapter.notifyDataSetChanged();
		}
		else
		{
			startScan();
		}
		database.close();
	}
	
	
	
	private void startScan()
	{
		HostEnumerator he = new HostEnumerator();
		he.setNetwork(_network);
		he.addHostSearchProgressListener(this);
		he.addHostSearchCompleteListener(this);
		he.execute();
	}
	
	

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Click listener //////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
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












