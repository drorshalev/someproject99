package com.ibus.autowol.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class HostEnumerator extends AsyncTask<Void, Host, Boolean> 
{
	private final String TAG = "HostEnumerator";
	private final static int[] DPORTS = { 139, 445, 22, 80 };
	IpAddress networkStart;
	IpAddress networkEnd;
	IpAddress gatewayIp;
	long netowrkSize;
	ListView _listToPopulate;
	
	public HostEnumerator(){}
	public HostEnumerator(IpAddress networkStart, IpAddress networkEnd, IpAddress gatewayIp, ListView listToPopulate)
	{
		this.networkStart = networkStart;
		this.networkEnd = networkEnd;
		this.gatewayIp = gatewayIp;
		this.netowrkSize = networkEnd.toLong() - networkStart.toLong() + 1;
		_listToPopulate = listToPopulate;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) 
	{
		Long start = networkStart.toLong();
		Long end = networkEnd.toLong();
		
		for (long i = start; i <= end; i++) 
		{
			Log.i(TAG, String.format("PROCESSING ITEM " + String.valueOf(i)));
			GetHost(new IpAddress(i));
        }
	
        Log.i(TAG, String.format("processing ended"));
        return true;
	}	
	
	@Override
	protected void onProgressUpdate (Host... host)
	{
		//_listToPopulate
		//host[0]
	}
	
	@Override
	protected void onPostExecute (Boolean result)
	{
		
	}
	
	
	public void GetHost(IpAddress addr) 
	{
		Host host = new Host();
		host.setIpAddress(addr);
		
		try 
		{
			//check if PC is reachable with java's version of ping.  this is pretty unreliable but i suspect it may force 
			//an update of the ARP table, and this table contains the mac addresses we need
			InetAddress h = InetAddress.getByName(host.getIpAddress().getAddress());
			if (h.isReachable(500)) 
		    {
		        Log.e(TAG, "PC is reachable or pingable: " + host.getIpAddress().getAddress());
		    }
			else
			{
				// A more reliable way of connecting to a pc and possibly of updating arp. note: we wont 
				//be able to connect to a pc if all of the tested ports are closed
	            int port;
	            Socket s = new Socket();
	            for (int i = 0; i < DPORTS.length; i++) 
	            {
                    s.bind(null);
                    s.connect(new InetSocketAddress(host.getIpAddress().getAddress(), DPORTS[i]), 500);
                    Log.v(TAG, "found using TCP connect " + host.getIpAddress().getAddress() + " on port=" + DPORTS[i]);
	            }
			}
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	        
		
		//attempt to get mac. even if pc is not found in arp this round it may be found in next round due to tests above
        if(SetMac(host)){
        	onProgressUpdate(host);
        	return;
        }                	
		
	}
	
	
	private boolean SetMac(Host host)
	{ 
		MacAddress mac = new MacAddress(host.getIpAddress());
		
		//check if a mac was found in our ARP table for the ip address 
        if(!mac.isEmpty())
        {
            Log.i(TAG, String.format("PC found using ARP check. Address: %s. MAC: %s", host.getIpAddress().getAddress(), mac.getAddress()));
            host.setMacAddress(mac);
            return true;
        }
        
        return false;
	}
	
	
	
}



