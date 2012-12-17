package com.ibus.autowol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity 
{	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    /** Called when the user clicks the Send button 
     * @throws IOException */
    public void sendMessage(View view) 
    {
    	String message = "";
    	
    	try 
    	{
    		
        	Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        	
        	 for (NetworkInterface netint : Collections.list(nets))
        	 {
				message += displayInfo(netint);
        	 }
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
    		
    	}
    	
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	editText.setText(message);
    	
    	
    	
    	//Intent intent = new Intent(this, DisplayMessageActivity.class);
    	//EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	//intent.putExtra(EXTRA_MESSAGE, message);
    	
    	//startActivity(intent);
    }
    
    
    static String displayInfo(NetworkInterface netint) throws IOException 
    {
        List inetAddresses = netint.getInterfaceAddresses();
        
        String iadd = "";
        short npl = 0;
        if(inetAddresses.size() > 0)
        {
        	iadd = ((InterfaceAddress)inetAddresses.get(0)).getAddress().toString();
        	npl = ((InterfaceAddress)inetAddresses.get(0)).getNetworkPrefixLength();
        }	
        
        return String.format("Display name: %s\n", netint.getDisplayName()) +
        String.format("Name: %s\n", netint.getName()) +
        String.format("Up? %s\n", netint.isUp()) +
        String.format("Loopback? %s\n", netint.isLoopback()) +
        String.format("PointToPoint? %s\n", netint.isPointToPoint()) +
        String.format("Supports multicast? %s\n", netint.supportsMulticast()) +
        String.format("Virtual? %s\n", netint.isVirtual()) +
        String.format("Hardware address: %s\n", Arrays.toString(netint.getHardwareAddress())) +
        String.format("MTU: %s\n", netint.getMTU()) +
        String.format("\n") +
        String.format("my ip address is: %s", iadd) +
        String.format("the sunnetmask address is: %d", npl);
        
        
        //String.format("the broadcast address of this subnet is:"+bcid);
        }
    
    
    
}


/*sudo apt-get install arp-scan
sudo arp-scan 192.168.1.0/24*/


//As said above we can get mac address from a known IP address if that host is in the same subnet. First ping that ip; then look at arp -a | grep and parse the string on nix* to get mac address.
//We can issue system command from all programming languages standard API's and can parse the output to get mac address.Java api can ping an IP but I am not sure if we parse the ping output(some library can do it). It would be better to avoid issuing system command and find an alternative solution as it is not really Platform Independent way of doing it.

//http://www.flattermann.net/2011/02/android-howto-find-the-hardware-mac-address-of-a-remote-host/
/**
 * Try to extract a hardware MAC address from a given IP address using the
 * ARP cache (/proc/net/arp).<br>
 * <br>
 * We assume that the file has this structure:<br>
 * <br>
 * IP address       HW type     Flags       HW address            Mask     Device
 * 192.168.18.11    0x1         0x2         00:04:20:06:55:1a     *        eth0
 * 192.168.18.36    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
 *
 * @param ip
 * @return the MAC from the ARP cache
 */
/*public static String getMacFromArpCache(String ip) {
    if (ip == null)
        return null;
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader("/proc/net/arp"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] splitted = line.split(" +");
            if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) {
                // Basic sanity check
                String mac = splitted[3];
                if (mac.matches("..:..:..:..:..:..")) {
                    return mac;
                } else {
                    return null;
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return null;
}
*/



/*byte[] mac = network.getHardwareAddress();

System.out.print("Current MAC address : ");

StringBuilder sb = new StringBuilder();
for (int i = 0; i < mac.length; i++) {
	sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
}*/









