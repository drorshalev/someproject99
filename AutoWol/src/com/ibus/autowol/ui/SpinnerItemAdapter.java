package com.ibus.autowol.ui;

import java.util.ArrayList;

import com.ibus.autowol.R;
import com.ibus.autowol.R.id;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


/**
 * A SpinnerItemAdapter to handle SpinnerItem Objects, 
 * displays the ArrayList of SpinnerItem Objects.
 */
public class SpinnerItemAdapter implements SpinnerAdapter{

    /**
     * The internal data, ArrayList of SpinnerItem Objects.
     */
	ArrayList<String> spinnerItem;
    private Activity _activity;

    public SpinnerItemAdapter(Activity context, ArrayList<String> spinnerItem){
        this.spinnerItem = spinnerItem;
        _activity = context;
    }

    /**
     * Returns the Size
     */
    @Override
    public int getCount() {
        return spinnerItem.size();
    }
    /**
     * Returns a SpinnerItem Object at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return spinnerItem.get(position);
    }

    /**
     * Views displayed when the Spinner is clicked, the drop 
     * down list of spinner items.
     */
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
    	View view= _activity.getLayoutInflater().inflate(android.R.layout.activity_list_item, null); 
        TextView v=(TextView)view.findViewById(R.id.activity_list_item_description);
        v.setText("adsf");     
        return v;
    }
    /**
     * Returns the View that is shown when a spinner item is selected.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	 View view= inflater.inflate(android.R.layout.activity_list_item, null); 
         TextView v=(TextView)view.findViewById(R.id.activity_list_item_description);
        
         
         v.setText("adsf");     
         return v;
    }

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	
}


