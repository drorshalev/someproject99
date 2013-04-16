package com.ibus.autowol.test;

import java.util.List;

import com.ibus.autowol.backend.Database;
import com.ibus.autowol.backend.Host;
import com.ibus.autowol.backend.Host.HostType;

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
                
        Host host = new Host();
        host.setName("name");
        host.setDisplayName("displayName");
        host.setIpAddress("12.12.12.12");
        host.setMacAddress("00:00:00:00:00:00");
        host.setNicVendor("nicName");
        
		db.saveHost(host);
		
		List<Host> hl = db.getAllHosts();
        
		assertTrue(hl.size() > 0);
		
        db.close();
	}
	
	
	
	
}
