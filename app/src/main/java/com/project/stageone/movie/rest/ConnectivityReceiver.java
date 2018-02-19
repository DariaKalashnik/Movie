package com.project.stageone.movie.rest;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static NetworkInfo networkInfo;
    private static ConnectivityManager connectivityManager;

    public ConnectivityReceiver() {
        super();
    }

    /**
     * Check the network connection
     */
    public static boolean isOnline(Context context) {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            networkInfo = connectivityManager.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (networkInfo != null && networkInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                Log.e("connection", "Online Connect Intenet ");
            } else {
                Log.e("connection", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}