package com.kalmahik.firstchat.api;


import com.kalmahik.firstchat.entities.Message;

public interface MessageReceiver {
    void onMessageReceived(Message message);
}
