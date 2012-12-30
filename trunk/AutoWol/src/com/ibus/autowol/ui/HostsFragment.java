package com.ibus.autowol.ui;

import java.util.ArrayList;
import java.util.HashSet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ibus.autowol.R;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.Host.HostType;
import com.ibus.autowol.backend.HostEnumerator;
import com.ibus.autowol.backend.HostListAdapter;
import com.ibus.autowol.backend.IpAddress;
import com.ibus.autowol.backend.MacAddress;
import com.ibus.autowol.backend.NetInfo;

public class HostsFragment extends SherlockFragment 
{
	//ArrayList<Host> hostList;
	HostListAdapter adapter;
	HashSet<View> selectedHosts = new HashSet<View>();
	public ActionMode actionMode;
	HostEnumerator hostEnumerator; 

	//
	// ActionBar Management /////////////////////////////////////////////////////////////////
	//
	
	public class ActionModeCallback implements ActionMode.Callback
	{
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) 
		{
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.hosts_selected_context_menu, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) 
		{
			switch (item.getItemId()) 
			{
	            case R.id.delete_host:
	            	mode.finish();
	                return false;
			}
			
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) 
		{
			ClearSelectedHostsList();
			actionMode = null;
		}
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) 
		{
			return false;
		}
	};
	
	
	//
	// List item click listener /////////////////////////////////////////////////////////////////
	//
	
	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) 
		{
			if(selectedHosts.contains(view))
			{
				view.setBackgroundColor(getResources().getColor(R.color.awol_white));
				selectedHosts.remove(view);
			}
			else
			{
				view.setBackgroundColor(getResources().getColor(R.color.awol_blue));
				selectedHosts.add(view);
			}
			
			if(selectedHosts.size() <= 0)
			{
				actionMode.finish();
			}
			else
				if(actionMode == null)
					actionMode = getSherlockActivity().startActionMode(new ActionModeCallback());
		}
	};
	
	
	public void ClearSelectedHostsList()
	{
		for (View host : selectedHosts) 
		{
			host.setBackgroundColor(getResources().getColor(R.color.awol_white));
		}
		
		selectedHosts.clear();
	}

	
	// Fragment methods /////////////////////////////////////////////////////////////////
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.host_fragment, container, false);
        return v;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) 
	{
		 super.onActivityCreated(savedInstanceState);
		
		 Host h = new Host(new IpAddress());
		 h.setHostType(HostType.PC);
		 h.setMacAddress(new MacAddress());
		 ArrayList<Host> l = new ArrayList<Host>();
		 l.add(h);
		 
		 adapter = new HostListAdapter(getActivity(), R.id.ip_address, l);
	     //adapter = new HostListAdapter(getActivity(), R.id.ip_address, new ArrayList<Host>());
	     
	     ListView listView = (ListView) getActivity().findViewById(R.id.host_list);
	     listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	     listView.setOnItemClickListener(onItemClickListener); 
	     listView.setAdapter(adapter);
	     
	     /*hostEnumerator = new HostEnumerator(NetInfo.network_start, NetInfo.network_end, NetInfo.gatewayIp, listView);
	     hostEnumerator.execute();*/
	    
	}
	
	
	
}















