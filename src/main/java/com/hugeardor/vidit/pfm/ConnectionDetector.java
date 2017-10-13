package com.hugeardor.vidit.pfm;

import android.app.Service;
import android.content.Context;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vidit on 12/10/17.
 */

public class ConnectionDetector {

    Context context ;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE) ;
        if(connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo() ;
            if(info != null){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return  true ;
                }
            }
        }
        return  false ;
    }
}
