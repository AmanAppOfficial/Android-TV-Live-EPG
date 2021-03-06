package com.streamly.TVApp.streamly.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.streamly.TVApp.streamly.R;


public class CommonUtility {
    public static final String TAG=CommonUtility.class.getSimpleName();

    public CommonUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static ProgressDialog custom_loader(Context context) {
        final ProgressDialog dialog = new ProgressDialog(context, R.style.MyProgressDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(ConstantUtility.LOADING);
        return dialog;
    }

    public static void checkInternetConnection(Context context){
        // Check network connection
        if (ConstantUtility.isNetworkConnected){
            //todo when Internet has Connected
        }else{
            //todo when Internet do not Connect
           // Toast.makeText(context, ConstantUtility.CHECK_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
