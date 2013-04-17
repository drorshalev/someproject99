package com.ibus.autowol.test;

import java.util.List;

import com.ibus.autowol.backend.Database;
import com.ibus.autowol.backend.Device;
import com.ibus.autowol.backend.Router;

import android.test.AndroidTestCase;

public class DatabaseTests extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreation()
	{
        Database db = new Database(getContext());
        db.open();
        db.close();
	}
	
	public void testSaveHost()
	{
        Database db = new Database(getContext());
        db.open();
                
        Device host = new Device();
        host.setName("name");
        host.setDisplayName("displayName");
        host.setIpAddress("12.12.12.12");
        host.setMacAddress("00:00:00:00:00:00");
        host.setNicVendor("nicName");
        
		db.saveHost(host);
		
		List<Device> hl = db.getAllHosts();
        
		assertTrue(hl.size() > 0);
		
        db.close();
	}
	
	
	public void testSaveRouter()
	{
        Database db = new Database(getContext());
        db.open();
                
        Router r = new Router();
        r.setName("name");
        r.setDisplayName("displayName");
        r.setIpAddress("12.12.12.12");
        r.setMacAddress("00:00:00:00:00:00");
        r.setNicVendor("nicName");
        r.setBssid("00:00:00:00:00:xx");
        r.setSsid("Ssid");
        
		db.saveRouter(r);
		
		List<Router> hl = db.getAllRouters();
        
		assertTrue(hl.size() > 0);
		
        db.close();
	}
	
	
	
	
}





