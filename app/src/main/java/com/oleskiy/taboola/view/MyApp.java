package com.oleskiy.taboola.view;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import androidx.multidex.MultiDexApplication;

import com.oleskiy.taboola.view.util.MyBroadcastReceiver;

public class MyApp extends MultiDexApplication {
    private static MyApp myApp=null;
    private MyBroadcastReceiver MyReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        IntentFilter intentFilter = new IntentFilter("com.oleskiy.taboola");
        MyReceiver = new MyBroadcastReceiver();
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }
    }

    public static MyApp getInstance(){
        return myApp;
    }
}
