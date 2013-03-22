package com.ibus.autowol.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class MacAddress implements Serializable
{
	private final static long serialVersionUID = 1L;
	private final static String TAG = "HardwareAddress";
    private final static String REQ = "select vendor from oui where mac=?";
    private final static String MAC_RE = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
    private final static int BUF = 8 * 1024;
	public static final String NOMAC = "00:00:00:00:00:00";
	
	private String _address;
	
	// Properties //////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_address == null) ? 0 : _address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MacAddress other = (MacAddress) obj;
		if (_address == null) {
			if (other._address != null)
				return false;
		} else if (!_address.equals(other._address))
			return false;
		return true;
	}

	public String getAddress() {
		return _address;
	}

	// Constructors //////////////////////////////////////////////////////////
	
	public MacAddress(){
		_address = NOMAC;
	}
	public MacAddress(String ipAddress)
	{
		setMacFromIp(ipAddress);
	}
	public MacAddress(IpAddress ipAddress)
	{
		setMacFromIp(ipAddress.getAddress());
	}
	
	// Methods //////////////////////////////////////////////////////////

	
	public void setMacFromIp(String ipAddress) 
	{
		_address = getMacAddress(ipAddress);
	}
	
	public boolean isEmpty()
	{
		return (_address.equals(NOMAC));
	}
	

    public static String getMacAddress(String ip) 
    {
        String hw = NOMAC;
        try {
            if (ip != null) {
                String ptrn = String.format(MAC_RE, ip.replace(".", "\\."));
                Pattern pattern = Pattern.compile(ptrn);
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"), BUF);
                String line;
                Matcher matcher;
                while ((line = bufferedReader.readLine()) != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        hw = matcher.group(1);
                        break;
                    }
                }
                bufferedReader.close();
            } else {
                Log.e(TAG, "ip is null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open/read file ARP: " + e.getMessage());
            return hw;
        }
        return hw;
    }
	
	
}
