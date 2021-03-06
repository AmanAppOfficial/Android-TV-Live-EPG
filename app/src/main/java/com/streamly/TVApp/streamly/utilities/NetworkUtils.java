package com.streamly.TVApp.streamly.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;


public class NetworkUtils {


    Context context;


    public NetworkUtils(Context context) {
        this.context = context;
    }

    // Network Check
    @SuppressLint("NewApi")
    public void registerNetworkCallback() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    ConstantUtility.isNetworkConnected = true; // Global Static Variable
                }

                @Override
                public void onLost(Network network) {
                    ConstantUtility.isNetworkConnected = false; // Global Static Variable

                }
            });
            ConstantUtility.isNetworkConnected = false;
        } catch (Exception e) {
            ConstantUtility.isNetworkConnected = false;
        }
    }
}
