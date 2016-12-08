package com.kalmahik.firstchat.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.activity.RegisterActivity;
import com.kalmahik.firstchat.api.MessageReceiver;
import com.kalmahik.firstchat.api.WSClient;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.storage.MessageDatabase;

public class MessageService extends Service implements MessageReceiver, Runnable {
    private boolean alive;
    private MessageDatabase messageDB;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(MessageService.class.getSimpleName(), "Service Run");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WSClient.getInstance().registerObserver(this);
        alive = true;
        new Thread(this).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        alive = false;
        WSClient.getInstance().unregisterObserver(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onMessageReceived(Message message) {
        //добавляем сообщение в базу
        messageDB = new MessageDatabase();
        messageDB.copyOrUpdate(message);

        //генерируем пуш уведомление, если нужно
        Log.d(MessageService.class.getSimpleName(), "PUSH: " + message);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(this,
                message.hashCode(),
                new Intent(this, RegisterActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New message")
                .setContentText(message.getBody())
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(intent);

        notificationManager.notify(message.hashCode(), builder.build());
    }

    @Override
    public void run() {
        while (alive) {
            WSClient.getInstance().connect();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}