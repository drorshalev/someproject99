package com.ibus.autowol.backend;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class Database {
	
	  private SQLiteDatabase db;
	  private final Context context;
	  private myDbHelper dbHelper;
	  
	  private static final int DATABASE_VERSION = 1;
	  private static final String DATABASE_NAME = "AutoWol.db";
	  	
	  //
	  // HOST TABLE
	  //
	  public static final String TABLE_HOST = "host";
	  public static final String COLUMN_HOST_ID="host_id";
	  public static final String COLUMN_HOST_NAME="name";
	  public static final String COLUMN_HOST_DISPLAY_NAME="display_name";
	  public static final String COLUMN_HOST_IP="ip";
	  public static final String COLUMN_HOST_MAC="mac";
	  public static final String COLUMN_HOST_NIC_VENDOR="nic_vendor";
	  
	  
	  private static final String SQL_CREATE_TABLE_HOST = 
		  "create table " 
		  + TABLE_HOST 
		  + " (" 
		  + COLUMN_HOST_ID + " integer primary key autoincrement, " 
		  + COLUMN_HOST_NAME + " text, "
		  + COLUMN_HOST_DISPLAY_NAME + " text, "
		  + COLUMN_HOST_IP + " text not null, "
		  + COLUMN_HOST_MAC + " text not null, "
		  + COLUMN_HOST_NIC_VENDOR +  " text"
		  + ");";
	  
	  private static final String SQL_DROP_TABLE_HOST = "DROP TABLE IF EXISTS " + TABLE_HOST;
	  
	  
	  public static final int ORDINAL_HOST_ID = 0;
	  public static final int ORDINAL_HOST_NAME = 1;
	  public static final int ORDINAL_HOST_DISPLAY_NAME = 2;
	  public static final int ORDINAL_HOST_IP = 3;
	  public static final int ORDINAL_HOST_MAC = 4;
	  public static final int ORDINAL_HOST_NIC_VENDOR = 5;
	  
	  
	  //
	  // ROUTER TABLE
	  //
	  public static final String TABLE_ROUTER = "router";
	  public static final String COLUMN_ROUTER_ID="router_id";
	  public static final String COLUMN_ROUTER_SSID="SSID"; //descriptive name of wifi hotspot
	  public static final String COLUMN_ROUTER_IP="ip";
	  public static final String COLUMN_ROUTER_MAC="mac";
	  public static final String COLUMN_ROUTER_BSSID="bssid";
	  public static final String COLUMN_ROUTER_NIC_VENDOR="nic_vendor";
	  
	  
	  private static final String SQL_CREATE_TABLE_ROUTER = 
		  "create table " 
		  + TABLE_ROUTER 
		  + " (" 
		  + COLUMN_ROUTER_ID + " integer primary key autoincrement, " 
		  + COLUMN_ROUTER_SSID + " text not null, "
		  + COLUMN_ROUTER_IP + " text not null, "
		  + COLUMN_ROUTER_MAC + " text not null, "
		  + COLUMN_ROUTER_BSSID + " text, "
		  + COLUMN_ROUTER_NIC_VENDOR +  " text"
		  + ");";
	  
	  private static final String SQL_DROP_TABLE_ROUTER = "DROP TABLE IF EXISTS " + TABLE_ROUTER;
	  
	  
	  public static final int ORDINAL_ROUTER_ID = 0;
	  public static final int ORDINAL_ROUTER_SSID = 1;
	  public static final int ORDINAL_ROUTER_IP = 2;
	  public static final int ORDINAL_ROUTER_MAC = 3;
	  public static final int ORDINAL_ROUTER_BSSID = 4;
	  public static final int ORDINAL_ROUTER_NIC_VENDOR = 5;
	  
	  
	  
	  ////////////////////////////////////////////////////////////////////////////////////
	  ////////////////////////////////////////////////////////////////////////////////////
	  ////////////////////////////////////////////////////////////////////////////////////
	  

	  private static final String SQL_GET_All_HOSTS = 
			  "select * from " + TABLE_HOST;
	  
	  private static final String SQL_GET_All_ROUTERS = 
			  "select * from " + TABLE_ROUTER;
	  
	  
	 /* public static final int INDEX_LOCATION_ID = 0;
	  public static final int INDEX_NAME = 1;
	  public static final int INDEX_LATITUDE = 2;
	  public static final int INDEX_LONGTITUDE = 3;
	  public static final int INDEX_ACCURACY = 4;
	  public static final int INDEX_ENABLED = 5;
	  
	  //
	  // ACTION TABLE
	  //
	  public static final String TABLE_ACTION = "action";
	  public static final String COLUMN_ACTION_ID="action_id";
	  public static final String COLUMN_ENTERING_ZONE ="entering_zone";
	  public static final String COLUMN_LEAVING_ZONE="leaving_zone";
	  public static final String COLUMN_LOCATION_FK="location_fk";
	  
	  private static final String SQL_CREATE_ACTION_TABLE = 
		  "create table " 
		  + TABLE_ACTION 
		  + " (" 
		  + COLUMN_ACTION_ID + " integer primary key autoincrement, " 
		  + COLUMN_ENTERING_ZONE + " integer not null, "
		  + COLUMN_LEAVING_ZONE + " integer not null, "
		  + COLUMN_LOCATION_FK + " integer not null"
		  + ");";
	  
	  public static final int INDEX_ACTION_ID = 0;
	  public static final int INDEX_ENTERING_ZONE = 1;
	  public static final int INDEX_LEAVING_ZONE = 2;
	  public static final int INDEX_LOCATION_FK = 3;
	  
	  //
	  // PLUGIN TABLE
	  //
	  public static final String TABLE_PLUGIN = "plugin";
	  public static final String COLUMN_PLUGIN_ID="plugin_id";
	  public static final String COLUMN_PACKAGE_NAME="package_name";
	  public static final String COLUMN_ACTIVITY_NAME="activity_name";
	  public static final String COLUMN_LABEL="label";
	  public static final String COLUMN_ACTION_FK="action_fk";
	 
	  private static final String SQL_CREATE_PLUGIN_TABLE = 
		  "create table " 
		  + TABLE_PLUGIN 
		  + " (" 
		  + COLUMN_PLUGIN_ID + " integer primary key autoincrement, " 
		  + COLUMN_PACKAGE_NAME + " text not null, "
		  + COLUMN_ACTIVITY_NAME + " text not null, "
		  + COLUMN_LABEL + " text not null, "
		  + COLUMN_ACTION_FK + " integer not null"
		  + ");";
	  
	  public static final int INDEX_PLUGIN_ID = 0;
	  public static final int INDEX_PACKAGE_NAME = 1;
	  public static final int INDEX_ACTIVITY_NAME = 2;
	  public static final int INDEX_LABEL = 3;
	  public static final int INDEX_ACTION_FK = 4;*/
	  
	  //
	  //SQL STATEMENTS
	  //
	  
	  
	  
	 /* private static final String SQL_GET_ALERT =
		  "select * from " 
		  + TABLE_LOCATION 
		  + " join " + TABLE_ACTION + " on " + COLUMN_LOCATION_ID + "=" + COLUMN_LOCATION_FK
		  + " join " + TABLE_PLUGIN + " on " + COLUMN_ACTION_ID + "=" + COLUMN_ACTION_FK
		  + " where " + COLUMN_LOCATION_ID + "=?";
	  
	  private static final String SQL_GET_LOCATION = 
		  "select * from " + TABLE_LOCATION + " where " + COLUMN_LOCATION_ID + "=?";
	  
	  private static final String SQL_GET_ACTION_FOR_LOCATION = 
		  "select * from " + TABLE_ACTION + " where " + COLUMN_LOCATION_FK + "=?";
	  
	  private static final String SQL_GET_PLUGIN_FOR_ACTION = 
		  "select * from " + TABLE_PLUGIN + " where " + COLUMN_ACTION_FK + "=?";
	  
	  private static final String SQL_GET_ALERTS_SHALLOW = 
		  "select * from " + TABLE_LOCATION;*/
	  
	  //
	  //TRIGGERS
	  //
	  /*public static final String TRIG_LOCATION_DELETE = "delete_"+ TABLE_LOCATION;
	  
	  private static final String SQL_LOCATION_DELETE =
	  "CREATE TRIGGER ["+TRIG_LOCATION_DELETE+"]" +
	  " BEFORE DELETE" +
	  " ON [" + TABLE_LOCATION + "]" +
	  " FOR EACH ROW" +
	  " BEGIN" +
	  " DELETE FROM " + TABLE_ACTION + " WHERE " + TABLE_ACTION + "." + COLUMN_LOCATION_FK + " = old." + COLUMN_LOCATION_ID + ";" +
	  " END";
	  
	  public static final String TRIG_ACTION_DELETE = "delete_"+ TABLE_ACTION;
	  
	  private static final String SQL_ACTION_DELETE =
	  "CREATE TRIGGER ["+TRIG_ACTION_DELETE+"]" +
	  " BEFORE DELETE" +
	  " ON [" + TABLE_ACTION + "]" +
	  " FOR EACH ROW" +
	  " BEGIN" +
	  " DELETE FROM " + TABLE_PLUGIN + " WHERE " + TABLE_PLUGIN + "." + COLUMN_ACTION_FK + " = old." + COLUMN_ACTION_ID + ";" +
	  " END";
	  */
	  
	  
	  //CONSTRUCTORS
	  
	  public Database(Context _context, int dbVersion) {
		  context = _context;
		  dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }
	  
	  public Database(Context _context) {
		  context = _context;
		  dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }
	  
	  public Database open() throws SQLException {
		  db = dbHelper.getWritableDatabase();
		  return this;
	  }
	  
	  
	  
	  //
	  // PUBLIC METHODS /////////////////////////////////////////////////////////
	  //
	  
	  public boolean isOpen() {
		  return db.isOpen();
	  }
		  
	  public void close() {
		  db.close();
	  }
	  
	  
	  public int saveHost(Device host)
	  {
		  ContentValues contentValues = new ContentValues();
		  contentValues.put(COLUMN_HOST_NAME, host.getName());
		  contentValues.put(COLUMN_HOST_DISPLAY_NAME, host.getDisplayName());
		  contentValues.put(COLUMN_HOST_IP, host.getIpAddress());
		  contentValues.put(COLUMN_HOST_MAC, host.getMacAddress());
		  contentValues.put(COLUMN_HOST_NIC_VENDOR, host.getNicVendor());
		  
		  return (int)db.insert(TABLE_HOST, null, contentValues);
	  }
	  
	  
	  public ArrayList<Device> getAllHosts() 
	  {
		  
		  ArrayList<Device> pal = new ArrayList<Device>();
		  Cursor cursor = db.rawQuery(SQL_GET_All_HOSTS, null);
		  
		  if(cursor.moveToFirst())
		  {
			do {
				Device h = new Device();
				h.setPrimaryKey(cursor.getInt(ORDINAL_HOST_ID));
				h.setName(cursor.getString(ORDINAL_HOST_NAME));
				h.setDisplayName(cursor.getString(ORDINAL_HOST_DISPLAY_NAME));
				h.setIpAddress(cursor.getString(ORDINAL_HOST_IP));
				h.setMacAddress(cursor.getString(ORDINAL_HOST_MAC));
				h.setNicVendor(cursor.getString(ORDINAL_HOST_NIC_VENDOR));
				
				pal.add(h);
		  	} 
			while(cursor.moveToNext());
			  
		  }
		  cursor.close();
			
		  return pal;
	  }
	  
	  
	  public int saveRouter(Router host)
	  {
		  ContentValues contentValues = new ContentValues();
		  
		  contentValues.put(COLUMN_ROUTER_SSID, host.getName());
		  contentValues.put(COLUMN_ROUTER_IP, host.getIpAddress());
		  contentValues.put(COLUMN_ROUTER_MAC, host.getMacAddress());
		  contentValues.put(COLUMN_ROUTER_BSSID, host.getBssid());
		  contentValues.put(COLUMN_ROUTER_NIC_VENDOR, host.getNicVendor());
		  
		  return (int)db.insert(TABLE_ROUTER, null, contentValues);
	  }
	  
	  
	  public ArrayList<Router> getAllRouters() 
	  {
		  
		  ArrayList<Router> pal = new ArrayList<Router>();
		  Cursor cursor = db.rawQuery(SQL_GET_All_ROUTERS, null);
		  
		  if(cursor.moveToFirst())
		  {
			do {
				
				Router h = new Router();
				h.setPrimaryKey(cursor.getInt(ORDINAL_ROUTER_ID));
				h.setName(cursor.getString(ORDINAL_ROUTER_SSID));
				h.setIpAddress(cursor.getString(ORDINAL_ROUTER_IP));
				h.setMacAddress(cursor.getString(ORDINAL_ROUTER_MAC));
				h.setBssid(cursor.getString(ORDINAL_ROUTER_BSSID));
				h.setNicVendor(cursor.getString(ORDINAL_ROUTER_NIC_VENDOR));
				
				pal.add(h);
		  	} 
			while(cursor.moveToNext());
			  
		  }
		  cursor.close();
			
		  return pal;
		  
		  
	  }
	  
	  
	  /*
	  private private IsValidDevice(Host device)
	  {
		  
		  
		  
		  private static final String SQL_CREATE_TABLE_HOST = 
				  "create table " 
				  + TABLE_HOST 
				  + " (" 
				  + COLUMN_HOST_ID + " integer primary key autoincrement, " 
				  + COLUMN_HOST_NAME + " text, "
				  + COLUMN_HOST_DISPLAY_NAME + " text, "
				  + COLUMN_HOST_IP + " text not null, "
				  + COLUMN_HOST_MAC + " text not null, "
				  + COLUMN_HOST_NIC_VENDOR +  " text"
				  + ");";
		  
		  
		  return device.
		  
	  }
	  */
	  
	 /* 
	  *//**
		 * insert or update an alert. if the alert has an id the method will update 
		 * otherwise it will create a new alert 
		 * 
		 * @alert the alert to save 
		 * @return returns the row that was inserted or updated.  -1 indicates an error occured 
		 *//*
	  public int saveAlert(ProximityAlert alert) {
		  
		  //NOTE: SHOULD PROBABLY BE USING TRANSACTIONS HERE
		  
		  //if we are updating an existing alert keep the 
		  //location parent but delete all child records
		  int locId = alert.id;
		  
		  if(locId == -1){
			  locId = AddLocationRow(alert);
			  if(locId < 0)
				  return -1; //error occured
		  }
		  else{
			  UpdateLocationRow(alert);
			  deleteActionsForLocation(locId); //triggers will ensure plugins are deleted too 
		  }
		  
		  for(ZoneAction za : alert.actions)
		  {
			  int actId = AddActionRow(za,locId);  
			  if(actId < 0)
				  return -1; //error occured
			  
			  for(Plugin pi : za.plugins)
			  {
				  int pinId = AddPluginRow(pi, actId);
				  if(pinId < 0)
					  return -1; //error occured
			  }
		  }
		
		  return locId;
	  }
	  
	  *//**
		 * Delete an alert. 
		 * 
		 * @param alertId. id of the item to delete
		 * @return returns the number of rows effected. Note: if delete 
		 * succeeds this will be one. internally however more than 1 recorded 
		 * will be deleted 
		 *//*
	  public int deleteAlert(int alertId) {
		  
		  String[] params = {((Integer)alertId).toString()};
		  
		  //delete operation useses cascade delete triggers so we don't have 
		  //to do anything other than delete the location. this will also delete 
		  //any attached actions and plugins
		  return db.delete(TABLE_LOCATION, COLUMN_LOCATION_ID + "=?" , params);
	  }
	  
	  
	  public int deleteActionsForLocation(int locationId) {
		  
		  String[] params = {((Integer)locationId).toString()};
		  
		  //delete operation useses cascade delete trigger so we don't have 
		  //to do anything other than delete the action.  this will also delet 
		  //any attached plugins
		  return db.delete(TABLE_ACTION, COLUMN_LOCATION_FK + "=?" , params);
	  }
	  
	  
	 *//**
		 * get alert as a cursor
		 * 
		 * @param alertId. id of the item to get
		 * @return a cursor
		 *//*
	  public Cursor getAlertCursor(int alertId) {
		  
		  String[] params = {((Integer)alertId).toString()};
		  return db.rawQuery(SQL_GET_ALERT, params);
	  }
	 
	 *//**
		 * get alert as an alert object
		 * 
		 * @param alertId. id of the item to get
		 * @return a ProximityAlert object.  this will be null 
		 * if no alert was found
		 *//*
	  public ProximityAlert getAlert(int alertId) {
		  
		  return getLocation(alertId);
	  }
	  
	  *//**
		 * get alert as an alert object. get alert record without child 
		 * action or plugin data 
		 * 
		 * @param alertId. id of the item to get
		 * @return a ProximityAlert object.  this will be null 
		 * if no alert was found
		 *//*
	  public ProximityAlert getAlertShallow(int alertId) {
		  
		  return getLocation(alertId, true);
	  }
	  
	  public ArrayList<ProximityAlert> getAlertsShallow() {
		  
		  ArrayList<ProximityAlert> pal = new ArrayList<ProximityAlert>();
		  Cursor cursor = db.rawQuery(SQL_GET_ALERTS_SHALLOW, null);
		  
		  if(cursor.moveToFirst())
		  {
			do {
				  pal.add(
						new ProximityAlert(
						cursor.getInt(INDEX_LOCATION_ID),
						cursor.getString(INDEX_NAME),
						cursor.getString(INDEX_LATITUDE),
						cursor.getString(INDEX_LONGTITUDE),
						cursor.getString(INDEX_ACCURACY),
						cursor.getInt(INDEX_ENABLED),
						new ArrayList<ZoneAction>())
				  );
		  	} while(cursor.moveToNext());
			  
		  }
		  cursor.close();
			
		  return pal;
	  }
	  
	  *//**
		 * update the enabled status of an alert
		 * 
		 * @param alertId. id of the alert to update
		 * @return the number of rows effected. <=0 would indicate 
		 * that the alert wasn't found or error occured 
		 *//*
	  public int updateAlertEnabledStatus(int alertId, boolean enabled)
	  {
		  ContentValues contentValues = new ContentValues();
		  contentValues.put(COLUMN_ENABLED, enabled);
		  
		  String[] params = {String.valueOf(alertId)};
		  return db.update(TABLE_LOCATION, contentValues, COLUMN_LOCATION_ID +"=?", params);
	  }
	  
	  
	  
	  
	  //just for testing
	  public void deleteDB() {
		  
		  //returns rows effected
		  db.delete(TABLE_PLUGIN, null, null);
		  db.delete(TABLE_ACTION, null, null);
		  db.delete(TABLE_LOCATION, null, null);
	  }
	  
	  
	  //
	  // PRIVATE METHODS /////////////////////////////////////////////////////////
	  //
	  
	
	
	
	  
	 
	 
	  private int UpdateLocationRow(ProximityAlert alert)
	  {
		  ContentValues contentValues = new ContentValues();
		  contentValues.put(COLUMN_NAME, alert.name);
		  contentValues.put(COLUMN_LATITUDE, alert.latitude);
		  contentValues.put(COLUMN_LONGTITUDE, alert.longtitude);
		  contentValues.put(COLUMN_ACCURACY, alert.radius);
		  contentValues.put(COLUMN_ENABLED, alert.enabled);
		  
		  String[] params = {String.valueOf(alert.id)};
		  return db.update(TABLE_LOCATION, contentValues, COLUMN_LOCATION_ID +"=?", params);
	  }
	  
	  
	  */
	  
	  
	  
	  
	  private static class myDbHelper extends SQLiteOpenHelper {
		  
		  	public myDbHelper(Context context, String name,
	                          CursorFactory factory, int version) {
	
		  		super(context, name, factory, version);
		    }
		  	
		    // Called when no database exists in
		    // disk and the helper class needs
		    // to create a new one.
		    @Override
		    public void onCreate(SQLiteDatabase _db) 
		    {
		    	_db.execSQL(SQL_CREATE_TABLE_HOST);
		    	_db.execSQL(SQL_CREATE_TABLE_ROUTER);
		    	
				//create cascade delete triggers
				/*_db.execSQL(SQL_LOCATION_DELETE);*/
		    }
		    
		    
		    
		    // Called when there is a database version mismatch meaning that
		    // the version of the database on disk needs to be upgraded to
		    // the current version.
		    @Override
		    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
		    {
		      
		    	_db.execSQL(SQL_DROP_TABLE_HOST);
		    	_db.execSQL(SQL_DROP_TABLE_ROUTER);
		    	
		      //drop cascade delete triggers
		      //_db.execSQL("DROP TRIGGER IF EXISTS " + TRIG_ACTION_DELETE);
		      
		      // Create a new db.
		      onCreate(_db);
		    }
		    
	  }
	  
	    
	  
	  
        
		 
} 
	  
	  
	  
	  /* Cursor cursor = db.query(
	  true, 
	  DATABASE_TABLE,
      new String[] {KEY_ID, KEY_ACTION},
      KEY_ID + "=" + _rowIndex,
      null, null, null, null, null);

cursor.moveToFirst();
return cursor.getString(ACTION_COLUMN);

	  
	  
if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
throw new SQLException("No to do item found for row: " +
              _rowIndex);
}
String task = cursor.getString(TASK_COLUMN);
long created = cursor.getLong(CREATION_DATE_COLUMN);
ToDoItem result = new ToDoItem(task, new Date(created));
return result;*/
	  
	  
	  
	  
	 /* private static final String DATABASE_TABLE = "IntentAction";
	  
	  	  
	  // Database structure
	  public static final String KEY_ID="_id";
	  public static final String KEY_ACTION="action";
	  public static final int ACTION_COLUMN = 1;
	  
	  private static final String DATABASE_CREATE = 
		  "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_ACTION + " text not null);";
	  

	  
	 
	  
	  
	  
	  public long insertEntry(String action) {
	    ContentValues contentValues = new ContentValues();
	    contentValues.put(KEY_ACTION, action);
	    return db.insert(DATABASE_TABLE, null, contentValues);
	  }
	 
	  public boolean removeEntry(long _rowIndex) {
	    return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	  }
	  
	  public Cursor getAllEntries () {
	    return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_ACTION},
	                    null, null, null, null, null);
	  }
	  
	  public String getEntry(long _rowIndex) {
		  
		  Cursor cursor = db.query(
				  true, 
				  DATABASE_TABLE,
                  new String[] {KEY_ID, KEY_ACTION},
                  KEY_ID + "=" + _rowIndex,
                  null, null, null, null, null);
		  
		  cursor.moveToFirst();
		  return cursor.getString(ACTION_COLUMN);
		  
		  	  
				  
		if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
		throw new SQLException("No to do item found for row: " +
		                  _rowIndex);
		}
		String task = cursor.getString(TASK_COLUMN);
		long created = cursor.getLong(CREATION_DATE_COLUMN);
		ToDoItem result = new ToDoItem(task, new Date(created));
		return result;
		
				 
	  }
	  
	  public int updateEntry(long _rowIndex, String action) {
	    
	    ContentValues contentValues = new ContentValues();
	    contentValues.put(KEY_ACTION, action);
	    return db.update(DATABASE_TABLE, contentValues, KEY_ID + "=" + _rowIndex, null);
	  }
*/

