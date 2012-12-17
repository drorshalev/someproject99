package com.ibus.autowol.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.util.Log;

public class PCEnumerator extends AsyncTask<Void, Host, String> 
{
	private final String TAG = "PCEnumerator";
	private final static int THREADS = 10; 
	private final static int TIMEOUT_SCAN = 3600; // seconds
	private final static int TIMEOUT_SHUTDOWN = 10; // seconds
	private final static int[] DPORTS = { 139, 445, 22, 80 };
	
	private ExecutorService mPool;
	IpAddress networkStart;
	IpAddress networkEnd;
	IpAddress gatewayIp;
	long netowrkSize;
	
	public PCEnumerator(){}
	public PCEnumerator(IpAddress networkStart, IpAddress networkEnd, IpAddress gatewayIp)
	{
		this.networkStart = networkStart;
		this.networkEnd = networkEnd;
		this.gatewayIp = gatewayIp;
		this.netowrkSize = networkEnd.toLong() - networkStart.toLong() + 1;
	}
	
	public void enumerate()
	{
		execute();
	}
	
	///
	//Main thread ////////////////////////////////////////////////////////////
	///
	@Override
	protected String doInBackground(Void... params) 
	{
		mPool = Executors.newFixedThreadPool(THREADS);
		
		Long start = networkStart.toLong();
		Long end = networkEnd.toLong();
		for (long i = start; i <= end; i++) {
			//Log.i(TAG, String.format("checking address: %d", i));
			launch(new IpAddress(i));
        }
		
		mPool.shutdown();
        try {
            if(!mPool.awaitTermination(TIMEOUT_SCAN, TimeUnit.SECONDS)){
                mPool.shutdownNow();
                Log.e(TAG, "Shutting down pool");
                if(!mPool.awaitTermination(TIMEOUT_SHUTDOWN, TimeUnit.SECONDS)){
                    Log.e(TAG, "Pool did not terminate");
                }
            }
        } catch (InterruptedException e){
            Log.e(TAG, e.getMessage());
            mPool.shutdownNow();
            Thread.currentThread().interrupt();
        } 
		
        Log.i(TAG, String.format("processing ended"));
        
		return null;
	}	
	
	private void launch(IpAddress address) 
	{
		if(!mPool.isShutdown()) {
			
			Host.HostType typ = Host.HostType.PC;
            if (address.equals(gatewayIp) ) 
            {
            	typ = Host.HostType.Gateway;
            }
			
		    mPool.execute(new PcDataCollectionThread(address, typ));
		}
	}
	 

	 @Override
	 protected void onProgressUpdate(Host... macAddress) 
	 {
		 /*String hh = macAddress[0].getHardwareAddress();
		 String dd = hh;*/
	 }
	
	 ///
	 //Child thread for each pc discovery task /////////////////////////////////////
	 ///
	 private class PcDataCollectionThread implements Runnable 
	 {
		private Host host;
		private int arpCheckCount = 0;    
		
		PcDataCollectionThread(IpAddress addr, Host.HostType hostType) 
		{
			host = new Host();
			host.setIpAddress(addr);
		}
		
		public void run() 
		{
			
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
            if(SetMac()){
	        	onProgressUpdate(host);
	        	return;
	        }                	
			
		}
		
		
		private boolean SetMac()
		{ 
			MacAddress mac = new MacAddress(host.getIpAddress());
			
			//check if a mac was found in our ARP table for the ip address 
            if(!mac.isEmpty())
            {
                Log.i(TAG, String.format("PC found using ARP check %d. Address: %s. MAC: %s", arpCheckCount, host.getIpAddress().getAddress(), mac.getAddress()));
                host.setMacAddress(mac);
                return true;
            }
            
            return false;
		}
		
		
		
	 }
	 
	 
}








