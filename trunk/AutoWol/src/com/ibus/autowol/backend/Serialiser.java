package com.ibus.autowol.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Context;
import android.util.Log;

public abstract class Serialiser 
{
	private static final String TAG = "Serialiser";
	
	public static void Serialise(Object objectToSave, String fileName, SherlockFragmentActivity activity)
	{
		
		ObjectOutputStream oos;
		try 
		{
			FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToSave); // write the class as an 'object'
			oos.flush(); // flush the stream to insure all of the information was written
			oos.close();// close the stream
		} 
		catch (FileNotFoundException e) 
		{
			Log.e(TAG, "Could not serialise object", e);	
		} 
		catch (IOException e) 
		{
			Log.e(TAG, "Could not serialise object", e);
		} 	
		finally
		{
			
		}
		
	}
	
	public static Object Deserialise(String fileName, SherlockFragmentActivity activity)
	{
		ObjectInputStream ois;
		try 
		{
			FileInputStream inp = activity.openFileInput(fileName);
			ois = new ObjectInputStream(inp);
			Object o = ois.readObject();
			return o;
		} 
		catch (StreamCorruptedException e) {
			Log.e(TAG, "Could not serialise object", e);	
		} 
		catch (FileNotFoundException e) {
			Log.e(TAG, "Could not serialise object", e);	
		} 
		catch (IOException e) {
			Log.e(TAG, "Could not serialise object", e);	
		} 
		catch (ClassNotFoundException e) {
			Log.e(TAG, "Could not serialise object", e);	
		}
		
		return null;
	}
}
