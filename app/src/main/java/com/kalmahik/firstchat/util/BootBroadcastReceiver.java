package com.kalmahik.firstchat.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kalmahik.firstchat.services.MessageService;
import com.kalmahik.firstchat.storage.UserPreferences;


public class BootBroadcastReceiver extends BroadcastReceiver{
    private UserPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = new UserPreferences(context);
        if (preferences.getToken() != null){
            context.startService(new Intent(context, MessageService.class));
        }
    }
}
