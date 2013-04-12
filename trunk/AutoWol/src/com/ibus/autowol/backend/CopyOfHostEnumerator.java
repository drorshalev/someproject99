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

public class CopyOfHostEnumerator extends AsyncTask<Void, Host, Boolean> 
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
	
	
	public CopyOfHostEnumerator(){}
	public CopyOfHostEnumerator(String networkStart, String networkEnd, String gatewayIp)
	{
		this.networkStart = networkStart;
		this.networkEnd = networkEnd;
		this.gatewayIp = gatewayIp;
		this.netowrkSize = IpAddress.getUnsignedLongFromString(networkEnd) - IpAddress.getUnsignedLongFromString(networkStart) + 1;
		_hostSearchProgressListeners = new ArrayList<OnHostSearchProgressListener>();
		_hostSearchCompleteListener = new ArrayList<OnHostSearchCompleteListener>(); 
	}
	
	
	
	
	/*private static final short   NETBIOS_UDP_PORT = 137;
	
	// NBT UDP PACKET: QUERY; REQUEST; UNICAST
	private static final byte[]  NETBIOS_REQUEST  = 
	{ 
		(byte)0x82, (byte)0x28, (byte)0x0,  (byte)0x0,  (byte)0x0, 
		(byte)0x1,  (byte)0x0,  (byte)0x0,  (byte)0x0,  (byte)0x0, 
		(byte)0x0,  (byte)0x0,  (byte)0x20, (byte)0x43, (byte)0x4B, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, (byte)0x41, 
		(byte)0x0,  (byte)0x0,  (byte)0x21, (byte)0x0,  (byte)0x1
	};
	
	
	public String GetNetbiosName(String ip)
	{
		InetAddress mAddress = null;
		DatagramSocket mSocket = null;
		try {
			mAddress = InetAddress.getByName(ip);
			mSocket    = new DatagramSocket();
			mSocket.setSoTimeout( 200 );
		} catch (UnknownHostException e1) 
		{
			Log.e("NBResolver", "UnknownHostException occured in GetNetbiosName", e1 );
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			Log.e("NBResolver", "SocketException occured in GetNetbiosName", e );
		}
		
		byte[] 		   buffer  = new byte[128];
		DatagramPacket packet  = new DatagramPacket( buffer, buffer.length, mAddress, NETBIOS_UDP_PORT );
		DatagramPacket query   = new DatagramPacket( NETBIOS_REQUEST, NETBIOS_REQUEST.length, mAddress, NETBIOS_UDP_PORT );
		String		   name    = null;
		String		   address = mAddress.getHostAddress();
		//Target 		   target  = null;

		for( int i = 0; i < 3; i++ )
		{				
			try
			{
				mSocket.send(query);
				mSocket.receive(packet);

				byte[] data = packet.getData();

				if( data != null && data.length >= 74 )
				{
					String response = new String(data, "ASCII" );

					// i know this is horrible, but i really need only the netbios name
					name = response.substring( 57, 73 ).trim();	
					if(name == null)
						name = "";

					Log.i( "NETBIOS", address + " was resolved to " + name );

					// update netbios cache
					mArpReader.addNetBiosName( address, name );

					// existing target
					target = System.getTargetByAddress( address );
					if( target != null )
					{
						target.setAlias( name );
						sendEndpointUpdateNotification( );
					}

					break;
				}						
			}				
			catch( SocketTimeoutException ste ) 
			{ 		
				// swallow timeout error
			}
			catch( IOException e )
			{
				Log.e("NBResolver", "IOException occured in GetNetbiosName", e );
			}
			finally
			{
				try
				{
					// send again a query
					mSocket.send( query );
				}
				catch( Exception e )
				{
					// swallow error
				}
			}
			
			
			int idd = 1;
		}

		mSocket.close();	
		
		
		return "";
	}
	*/
	
	
	
	
	
	
	
	@Override
	protected Boolean doInBackground(Void... params) 
	{
		
		
		
		
		
		/*Long start = networkStart.toLong();
		Long end = networkEnd.toLong();
	        
		for (long i = start; i <= end; i++) 
		{
			IpAddress ip = new IpAddress(i);
			Ping(ip.getAddress());
	    }*/
		
		
		/*Log.i("NetworkScanActivity", " ******** DONE ********************");
		
		return true;*/
		
		Log.i(TAG, "Host enumeration starting");
		
	    /*ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
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
	        if(SetMac(host)){
	        	return host;
	        }           
	        
			return null;
		}
		
		
		private boolean SetMac(Host host)
		{ 
			/*MacAddress2 mac = new MacAddress2(host.getIpAddress());
			
			//check if a mac was found in our ARP table for the ip address 
	        if(!mac.isEmpty())
	        {
	            Log.i(TAG, String.format("PC found using ARP check. Address: %s. MAC: %s", host.getIpAddress(), mac.getAddress()));
	            host.setMacAddress(mac);
	            return true;
	        }*/
	        
	        return false;
		}
	
	}
	
}



