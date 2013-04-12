package com.ibus.autowol.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jcifs.netbios.Name;
import jcifs.netbios.NbtAddress;

import android.os.AsyncTask;
import android.util.Log;

import com.ibus.autowol.backend.Host.HostType;
import com.ibus.autowol.ui.OnHostSearchCompleteListener;
import com.ibus.autowol.ui.OnHostSearchProgressListener;

public class HostEnumerator extends AsyncTask<Void, Host, Boolean> 
{
	private static final int NTHREDS = 10;
	private final String TAG = "HostEnumerator";
	private final static int[] DPORTS = { 139, 445, 22, 80 };
	String networkStart;
	String networkEnd;
	String gatewayIp;
	long netowrkSize;
	List<OnHostSearchProgressListener> _hostSearchProgressListeners;
	List<OnHostSearchCompleteListener> _hostSearchCompleteListener; 
	
	
	public HostEnumerator(){}
	public HostEnumerator(String networkStart, String networkEnd, String gatewayIp)
	{
		this.networkStart = networkStart;
		this.networkEnd = networkEnd;
		this.gatewayIp = gatewayIp;
		this.netowrkSize = IpAddress.getUnsignedLongFromString(networkEnd) - IpAddress.getUnsignedLongFromString(networkStart) + 1;
		_hostSearchProgressListeners = new ArrayList<OnHostSearchProgressListener>();
		_hostSearchCompleteListener = new ArrayList<OnHostSearchCompleteListener>(); 
	}
	
	
	
	
	
	
	@Override
	protected Boolean doInBackground(Void... params) 
	{
		Long start = IpAddress.getUnsignedLongFromString(networkStart);
		Long end = IpAddress.getUnsignedLongFromString(networkEnd);
		List<Host> hosts = null;
		try 
		{
			for (long i = start; i <= end; i++) 
			{
		      Udp.probe(IpAddress.getStringFromLongUnsigned(i));
		    }
		
			Thread.sleep(1000);
			
			hosts = Arp.EnumerateHosts();
			
			for(Host h : hosts)
			{
				String n = InetAddressManager.GetHostName(h.getIpAddress());
				if(n == null)
					n = Jcifs.getHostName(h.getIpAddress());
					
				h.setName(n);
			}
			
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	
		
		
		
		
		/*Log.i(TAG, "Host enumeration starting");
		
	    ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
	    List<Future<Host>> list = new ArrayList<Future<Host>>();
	    
	    Long start = networkStart.toLong();
		Long end = networkEnd.toLong();
	        
		for (long i = start; i <= end; i++) 
		{
	      Callable<Host> worker = new HostEnumerationCallable(new IpAddress(i));
	      Future<Host> submit = executor.submit(worker);
	      list.add(submit);
	    }
	
	    // Now retrieve the result
	    for (Future<Host> future : list) 
	    {
	      try 
	      {
	    	  Host h = future.get();
	    	  if(h != null)
	    		  Log.i(TAG, "found host: " + h.getIpAddress().getAddress());
	    	  
	    	  this.publishProgress(h);
	    		
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      } catch (ExecutionException e) {
	        e.printStackTrace();
	      }
	    }
	    
	    executor.shutdown();*/
	    return true;
	}	
	
	@Override
	protected void onProgressUpdate (Host... host)
	{
		if(host[0] != null)
		{
			Log.i(TAG, "updating list item for: " + host[0].getIpAddress());
		
			for (OnHostSearchProgressListener listener : _hostSearchProgressListeners) 
			{
				listener.onHostSearchProgress(host[0]);
	        }
		}
		else
		{
			for (OnHostSearchProgressListener listener : _hostSearchProgressListeners) 
			{
				listener.onHostSearchProgress(null);
	        }
		}
		
	}
	
	
	@Override
	protected void onPostExecute (Boolean result)
	{
		Log.i(TAG, "Host enumeration complete");
		
		for (OnHostSearchCompleteListener listener : _hostSearchCompleteListener) 
		{
			listener.onSearchComplete();
        }
	}
	
	
	public void addHostSearchProgressListener(OnHostSearchProgressListener listener) {
		_hostSearchProgressListeners.add(listener);
    }
	
	public void addHostSearchCompleteListener(OnHostSearchCompleteListener listener) {
		_hostSearchCompleteListener.add(listener);
    }
	
	
	///
	// HostEnumerationCallable /////////////////////////////////////////////////////////////////////////
	///
	
	public class HostEnumerationCallable implements Callable<Host> 
	{
		String _ipAddress;
		
		public HostEnumerationCallable(String ipAddress)
		{
			_ipAddress = ipAddress;
		}
		
		
		public Host call() throws Exception 
		{
			return GetHost(_ipAddress);
		}
		
		public Host GetHost(String addr) 
		{
			Host host = new Host();
			host.setIpAddress(addr);
			host.setDeviceType(HostType.PC);
			
			Log.i(TAG, "interogating: " + host.getIpAddress());
			
			try 
			{
				//check if PC is reachable with java's version of ping.  this is pretty unreliable but i suspect it may force 
				//an update of the ARP table, and this table contains the mac addresses we need
				InetAddress h = InetAddress.getByName(host.getIpAddress());
				if (h.isReachable(500)) 
			    {
			        Log.e(TAG, "PC is reachable or pingable: " + host.getIpAddress());
			    }
				else
				{
					// A more reliable way of connecting to a pc and possibly of updating arp. note: we wont 
					//be able to connect to a pc if all of the tested ports are closed
		            Socket s = new Socket();
		            for (int i = 0; i < DPORTS.length; i++) 
		            {
	                    s.bind(null);
	                    s.connect(new InetSocketAddress(host.getIpAddress(), DPORTS[i]), 500);
	                    Log.v(TAG, "found using TCP connect " + host.getIpAddress() + " on port=" + DPORTS[i]);
		            }
				}
				
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		        
			
			//attempt to get mac. even if pc is not found in arp this round it may be found in next round due to tests above
	        /*if(SetMac(host)){
	        	return host;
	        }           
	        */
			return null;
		}
		
		
		
	
	}
	
}



