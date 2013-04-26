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

public class MockNetwork implements INetwork
{
	String _netmaskIp;
	String _gatewayIp;
	String _networkEndIp;
	String _networkStartIp;
	Router _router = new Router();
	boolean _isConnectedToNetwork;
	
	public MockNetwork()
	{
		_netmaskIp = "255.255.255.0";
		_gatewayIp = "10.0.0.140";
		_networkStartIp= "10.0.0.1";
		_networkEndIp = "10.0.0.254";
		_isConnectedToNetwork = true;
		
		_router.setBssid(MacAddress.getEmptyMac());
    	_router.setSsid("Fakety fake");
    	_router.setDisplayName("Fake");
    	_router.setIpAddress(IpAddress.GetEmptyIp());
    	_router.setMacAddress(MacAddress.getEmptyMac());
    	_router.setName("Fake");
    	_router.setNicVendor("Fake");
    	_router.setPrimaryKey(0);
	}
	
    public Router getRouter() 
    {
		return _router;
	}
    
    public void setRouter(Router router) 
    {
		_router = router;
	}
    
    
	public String getNetmaskIp() {
		return _netmaskIp;
	}
	public void setNetmaskIp(String ip) {
		_netmaskIp = ip;
	}
	
	public String getGatewayIp() {
		return _gatewayIp;
	}
	public void setGatewayIp(String ip) {
		_gatewayIp = ip;
	}
	
	public String getNetworkEndIp() {
		return _networkEndIp;
	}
	public void setNetworkEndIp(String ip) {
		_networkEndIp = ip;
	}
	
	public String getNetworkStartIp() {
		return _networkStartIp;
	}
	public void setNetworkStartIp(String ip) {
		_networkStartIp = ip;	
	}
	
	
	public void refresh(final Context ctxt){}
	
	public boolean IsGateway(String ipAddress)
    {
    	if(ipAddress == null)
    		return false;
    	
    	return ipAddress.equals(_router.getIpAddress());
    }

    public boolean isConnectedToNetwork(Context ctxt) 
    {
        return _isConnectedToNetwork;
    }
    
    public void setIsConnectedToNetwork(boolean isConnected)
    {
    	_isConnectedToNetwork = isConnected;
    }
}




