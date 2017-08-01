package com.onesown;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StorageOptions extends CordovaPlugin {

    private static final String TAG = "StorageOptions";

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

	@Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

		if(action.equals("getStoragePath")) {
		
			String speicherort = args.getString(0);
            String pfad = this.getStoragePath(speicherort);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, pfad));

			return true;

        }
        else if(action.equals("getFreeMemory")) {

			String speicherort = args.getString(0);
            String freeMemory = this.getFreeMemory(speicherort);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, freeMemory));

			return true;

        }
        else if(action.equals("getFreeBytes")) {

			String speicherort = args.getString(0);
            long freeBytes = this.getFreeBytes(speicherort);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, freeBytes));

			return true;

        }
		
        return false;

    }

    private String getStoragePath(String speicherort) {
       
		Context context = cordova.getActivity().getApplicationContext(); 
        File[] fs = context.getExternalFilesDirs(null);

        if (fs != null){

            if(speicherort.equals("intern")){
                File fileIntern = fs[0];

                if (fileIntern != null){
                    return fileIntern.getAbsolutePath();
                }
            }
    
            if(fs.length >= 2 && speicherort.equals("extern")){
                File fileExtern = fs[1];
    
                if (fileExtern != null){
                    return fileExtern.getAbsolutePath();
                }
            }
        }
        
        return null;
    }
	
	private long getFreeBytes(String speicherort) {
        
		Context context = cordova.getActivity().getApplicationContext(); 
        File[] fs = context.getExternalFilesDirs(null);

		if (fs != null){

			// fs[0] ist der erste "interne" Speicher.
			if(speicherort.equals("intern") && fs[0] != null){
				return computeFreeMemory(fs[0]);
			}

			// fs[1] ist der zweite "externe" Speicher.
			if(fs.length >= 2 && speicherort.equals("extern") && fs[1] != null){
				return computeFreeMemory(fs[1]);
			}

		}

		return 0;
    }
	
	private String getFreeMemory(String speicherort) {
        
		Context context = cordova.getActivity().getApplicationContext(); 
        File[] fs = context.getExternalFilesDirs(null);
		String sizeAsReadableString = null;
		long size;

		if (fs != null){

			// fs[0] ist der erste "interne" Speicher.
			if(speicherort.equals("intern") && fs[0] != null){

				size = computeFreeMemory(fs[0]);
				sizeAsReadableString = Formatter.formatFileSize(context, size); // formatFileSize || formatShortFileSize -> fuer kuerzere Angaben.

				return sizeAsReadableString;

			}

			// fs[1] ist der zweite "externe" Speicher.
			if(fs.length >= 2 && speicherort.equals("extern") && fs[1] != null){

				size = computeFreeMemory(fs[1]);
				sizeAsReadableString = Formatter.formatFileSize(context, size);

				return sizeAsReadableString;

			}

		}

		return sizeAsReadableString;
    }
	
	// Get free space for provided path
	// Note that this will throw IllegalArgumentException for invalid paths
	private long computeFreeMemory(File path) {

		try{

			StatFs stats = new StatFs(path.getAbsolutePath());
			return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();


		} catch(Exception e){
		}

		return 0;
        
    }

}
