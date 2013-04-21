package com.ibus.autowol.backend;

import com.ibus.autowol.mock.EmulatorNetwork;

public class Factory 
{
	//ONLY SET TO TRUE IF WE ARE IN AN EMULATOR. 
	private static boolean inEmulator = false;
	
	private static INetwork _network;
	private static IHostEnumerator _hostEnumerator;
		  
	public static void setNetwork(INetwork creditCardProcessor) {
		_network = creditCardProcessor;
	}
	  
	public static void setHostEnumerator(IHostEnumerator hostEnumerator) {
		_hostEnumerator = hostEnumerator;
	}
	
	public static INetwork getNetwork() 
	{
		if(inEmulator)
			return new EmulatorNetwork(); //if we are in an emulator
		
		if (_network == null) {
			return (INetwork) new Network();
		}
		
		return _network;
	}
	  
	public static IHostEnumerator getHostEnumerator() 
	{
		if (_hostEnumerator == null) {
			return (IHostEnumerator) new NetworkScanner();
	    }
	    
		return _hostEnumerator;
	}
	  
	
	
	private void GetFakeNetwork()
	{
		
	}
	  
}
