package com.ibus.autowol.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ibus.autowol.R;

public class PromptNetworkScanDialog extends DialogFragment 
{
	public interface PromptNetworkScanDialogListener 
	{
		public void onPromptNetworkScanPositiveClick(DialogInterface dialog);
		public void onPromptNetworkScanNegativeClick(DialogInterface dialog);
	}
   
   	PromptNetworkScanDialogListener _listener;
	
   	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) 
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setMessage(R.string.prompt_network_scan_dialog_fragment_message)
               .setPositiveButton(R.string.prompt_network_scan_dialog_fragment_ok, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) {
                         _listener.onPromptNetworkScanPositiveClick(dialog);
                   }
               })
               .setNegativeButton(R.string.prompt_network_scan_dialog_fragment_cancel, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) {
                	   _listener.onPromptNetworkScanNegativeClick(dialog);
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
   	
   	@Override
    public void onAttach(Activity activity) 
    {
   		super.onAttach(activity);
   		
        try 
        {
        	_listener = (PromptNetworkScanDialogListener) activity;
        } 
        catch (ClassCastException e) 
        {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
}











