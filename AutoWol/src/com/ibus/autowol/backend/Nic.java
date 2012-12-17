package com.ibus.autowol.backend;

public class Nic 
{
	private String _name;
	private IpAddress _address;
	
	//
	// Properties ////////////////////////////////////////////
	//
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public IpAddress getAddress() {
		return _address;
	}
	public void setAddress(IpAddress address) {
		_address = address;
	}
	
	//
	// Constructors ////////////////////////////////////////////
	//
	
	public Nic(){
		_address = new IpAddress();
	}
	

	//
	// Methods ////////////////////////////////////////////
	//
	
	
}
