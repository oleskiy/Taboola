package com.oleskiy.taboola.view.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.oleskiy.taboola.view.api.Repository;

public class MyBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Data Received from External App for change item's color", Toast.LENGTH_SHORT).show();
        int index = intent.getIntExtra("index",-1);
        int color = intent.getIntExtra("color",-1);
        int id =DBHelper.getInstance().updateColor(index, color);
        if(id>0){
            Repository.instance().updateColor(index, color);
        }
    }
}
