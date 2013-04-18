/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

//am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings
package com.ibus.autowol.backend;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public abstract class Network 
{
    //private SharedPreferences prefs;
	private static Context _ctxt;
    private static final String TAG = "Network";
    
    private static String _networkStartIp;
    private static String _networkEndIp;
    private static String _gatewayIp;
    private static String _netmaskIp;
    private static Device _device;
    private static Cidr _cidr;    
    private static String _carrier = null;
    private static WifiInfo wifiInfo;

    
    public static String getNetmaskIp() {
		return _netmaskIp;
	}
	public static String getGatewayIp() {
		return _gatewayIp;
	}
	public static String getNetworkEndIp() {
		return _networkEndIp;
	}
	public static String getNetworkStartIp() {
		return _networkStartIp;
	}
	public static String getNetworkName() {
		return wifiInfo.getSSID();
	}
	
	public static void refresh(final Context ctxt)
    {
    	Network._ctxt = ctxt;
    	setDevice();
    	setWifiInfo();
    	
    	setHostBounds();
    }
    
	
    //set ip of the device to the first valid ip found a network interface
    private static void setDevice() 
    {
    	_device = new Device();
        try 
        {
        	for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics.hasMoreElements();) 
        	{
                NetworkInterface nic = nics.nextElement();
                
                String ip = getInterfaceFirstIp(nic);
                if (IpAddress.isValidIp(ip)) 
                {
                	_device.setName(nic.getName());
                	_device.setIpAddress(ip);
                	_device.setMacAddress(MacAddress.getStringFromBytes(nic.getHardwareAddress()));
                    break;
                }
            }
            
        } catch (SocketException e) {
            Log.e(TAG, e.getMessage());
        }
        
    }
    

    private static boolean setWifiInfo() 
    {
        WifiManager wifiManager = (WifiManager) _ctxt.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) 
        {
        	_gatewayIp = IpAddress.getStringFromIntSigned(wifiManager.getDhcpInfo().gateway);
        	_netmaskIp = IpAddress.getStringFromIntSigned(wifiManager.getDhcpInfo().netmask);
        	wifiInfo = wifiManager.getConnectionInfo();
        	
            return true;
        }
        return false;
    }
    
    
    private static void setHostBounds()
    {
    	_cidr = new Cidr(_netmaskIp);
    	
    	long numericDeviceIp = IpAddress.getUnsignedLongFromString(_device.getIpAddress()); 
    	
    	// Detected IP
        int shift = (32 - _cidr.getCidr());
        long start;
        long end;
        if (_cidr.getCidr() < 31) {
        	start =  (numericDeviceIp >> shift << shift) + 1;
        	end =  (start | ((1 << shift) - 1)) - 1;
        } 
        else 
        {
        	start =  (numericDeviceIp >> shift << shift);
        	end =  (start | ((1 << shift) - 1));
        }
        
        _networkStartIp = IpAddress.getStringFromLongUnsigned(start);
    	_networkEndIp = IpAddress.getStringFromLongUnsigned(end);
    }

    
    
    public static boolean IsGateway(String ipAddress)
    {
    	if(ipAddress == null)
    		return false;
    	
    	return ipAddress.equals(_gatewayIp);
    }
    
    
    
    
    private static String getInterfaceFirstIp(NetworkInterface ni) 
    {
        if (ni != null) 
        {
            for (Enumeration<InetAddress> nis = ni.getInetAddresses(); nis.hasMoreElements();) 
            {
                InetAddress ia = nis.nextElement();
                if (!ia.isLoopbackAddress()) 
                {
                    if (ia instanceof Inet6Address) 
                    {
                        Log.i(TAG, "IPv6 detected and not supported yet!");
                        continue;
                    }
                    
                    return ia.getHostAddress();
                }
            }
        }
        
        return null;
    }

    
    public static boolean isConnectedToNetwork(Context ctxt) 
    {
        NetworkInfo nfo = ((ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (nfo != null) {
            return nfo.isConnected();
        }
        
        return false;
    }
    
    
    //* UNUSED 8888888888888888888888888888888888888888888888888888888888888888888888888888888888888 */
    


    public static boolean getMobileInfo() {
        TelephonyManager tm = (TelephonyManager) _ctxt.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            _carrier = tm.getNetworkOperatorName();
        }
        return false;
    }

    public static String getNetIp() 
    {
        int shift = (32 - _cidr.getCidr());
        int start = ((int) IpAddress.getUnsignedLongFromString(_device.getIpAddress()) >> shift << shift);
        return IpAddress.getStringFromLongUnsigned((long) start);
    }


    
    
    
   /* public String getCurrentSsid(Context context) {

    	  String ssid = null;
    	  ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	  NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	  
    	  if (networkInfo.isConnected()) 
    	  {
    	    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	    
    	    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
    	    if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
    	        //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
    	      ssid = connectionInfo.getSSID();
    	    }
    	    
    	 // Get WiFi status MARAKANA
    	    WifiInfo info = wifiManager.getConnectionInfo();
    	    String textStatus = "";
    	    textStatus += "\n\nWiFi Status: " + info.toString();
    	    String BSSID = info.getBSSID();
    	    String MAC = info.getMacAddress();

    	    
    	    
    	    
    	    
    	    
    	    List<ScanResult> results = wifiManager.getScanResults();
    	    ScanResult bestSignal = null;
    	    int count = 1;
    	    String etWifiList = "";
    	    for (ScanResult result : results) {
    	        etWifiList += count++ + ". " + result.SSID + " : " + result.level + "\n" +
    	                result.BSSID + "\n" + result.capabilities +"\n" +
    	                "\n=======================\n";
    	    }
    	    Log.v(TAG, "from SO: \n"+etWifiList);

    	    // List stored networks
    	    List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
    	    for (WifiConfiguration config : configs) {
    	        textStatus+= "\n\n" + config.toString();
    	    }
    	    Log.v(TAG,"from marakana: \n"+textStatus);
    	  }
    	  return ssid;
    	}*/
    
    

    
    
    /*
     * public String getIp() { return getIpFromIntSigned(dhcp.ipAddress); }
     * public int getNetCidr() { int i = dhcp.netmask; i = i - ((i >> 1) &
     * 0x55555555); i = (i & 0x33333333) + ((i >> 2) & 0x33333333); return ((i +
     * (i >> 4) & 0xF0F0F0F) * 0x1010101) >> 24; // return 24; } public String
     * getNetIp() { return getIpFromIntSigned(dhcp.ipAddress & dhcp.netmask); }
     */
    // public String getNetmask() {
    // return getIpFromIntSigned(dhcp.netmask);
    // }

    // public String getBroadcastIp() {
    // return getIpFromIntSigned((dhcp.ipAddress & dhcp.netmask) |
    // ~dhcp.netmask);
    // }

    // public Object getGatewayIp() {
    // return getIpFromIntSigned(dhcp.gateway);
    // }

   /* public static SupplicantState getSupplicantState() {
        return wifiConnectionInfo.getSupplicantState();
    }*/

    

    // public int getIntFromInet(InetAddress ip_addr) {
    // return getIntFromIp(ip_addr.getHostAddress());
    // }

    // private InetAddress getInetFromInt(int ip_int) {
    // byte[] quads = new byte[4];
    // for (int k = 0; k < 4; k++)
    // quads[k] = (byte) ((ip_int >> k * 8) & 0xFF); // 0xFF=255
    // try {
    // return InetAddress.getByAddress(quads);
    // } catch (java.net.UnknownHostException e) {
    // return null;
    // }
    // }
}





/*public int speed = 0;
public String ssid = null;
public String bssid = null;
public String macAddress = NOMAC;
public String gatewayIp = NOIP;
public String netmaskIp = NOMASK;*/

/*info = wifi.getConnectionInfo();
// Set wifi variables
speed = info.getLinkSpeed();
ssid = info.getSSID();
bssid = info.getBSSID();
macAddress = info.getMacAddress();
gatewayIp = getIpFromIntSigned(wifi.getDhcpInfo().gateway);
// broadcastIp = getIpFromIntSigned((dhcp.ipAddress & dhcp.netmask)
// | ~dhcp.netmask);
netmaskIp = getIpFromIntSigned(wifi.getDhcpInfo().netmask);*/




//
////sets the start and end address of the local network as a long
//private void setHostBounds()
//{
//	long network_ip = deviceIp.getNumericAddress(); 
//	network_start = new IP
//	
//	// Detected IP
//  int shift = (32 - cidr);
//  if (cidr < 31) {
//      network_start = (network_ip >> shift << shift) + 1;
//      network_end = (network_start | ((1 << shift) - 1)) - 1;
//  } else {
//      network_start = (network_ip >> shift << shift);
//      network_end = (network_start | ((1 << shift) - 1));
//  }
//}
//

