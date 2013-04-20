/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

//am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings
package com.ibus.autowol.mock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

import com.ibus.autowol.backend.Cidr;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.Router;

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
    private static Router router;
    
    public static Router getRouter() {
		return router;
	}
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
	
	
	public static void refresh(final Context ctxt)
    {
    	
    }
	
	public static boolean IsGateway(String ipAddress)
    {
    	if(ipAddress == null)
    		return false;
    	
    	return ipAddress.equals(_gatewayIp);
    }
	

    
    public static boolean isConnectedToNetwork(Context ctxt) 
    {
        NetworkInfo nfo = ((ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (nfo != null) {
            return nfo.isConnected();
        }
        
        return false;
    }
    
}




