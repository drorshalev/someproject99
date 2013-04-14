package com.ibus.autowol.ui;

public class NetworkListItem 
{
	private String _networkName;

	public String getNetworkName() {
		return _networkName;
	}

	public void setNetworkName(String networkName) {
		_networkName = networkName;
	} 
	
	public NetworkListItem()
	{
	}
	public NetworkListItem(String networkName)
	{
		_networkName = networkName;
	}
}
