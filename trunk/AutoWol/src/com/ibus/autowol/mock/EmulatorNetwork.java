package com.ibus.autowol.mock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

import com.ibus.autowol.backend.Cidr;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.INetwork;
import com.ibus.autowol.backend.IpAddress;
import com.ibus.autowol.backend.MacAddress;
import com.ibus.autowol.backend.Router;

public class EmulatorNetwork implements INetwork
{
    public Router getRouter() 
    {
    	Router r = new Router();
    	r.setBssid(MacAddress.getEmptyMac());
    	r.setSsid("Fakety fake");
    	
    	r.setDisplayName("Fake");
    	r.setIpAddress(IpAddress.GetEmptyIp());
    	r.setMacAddress(MacAddress.getEmptyMac());
    	r.setName("Fake");
    	r.setNicVendor("Fake");
    	r.setPrimaryKey(0);
    	
		return r;
	}
	public String getNetmaskIp() {
		return "255.255.255.0";
	}
	public String getGatewayIp() {
		return "10.0.0.138";
	}
	public String getNetworkEndIp() {
		return "10.0.0.1";
	}
	public String getNetworkStartIp() {
		return "10.0.0.254";
	}
	
	public void refresh(final Context ctxt){}
	
	public boolean IsGateway(String ipAddress)
    {
    	return false;
    }

    public boolean isConnectedToNetwork(Context ctxt) 
    {
        
        return true;
    }
    
}




