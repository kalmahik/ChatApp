package com.kalmahik.firstchat.api;

import android.util.Log;

import com.google.gson.Gson;
import com.kalmahik.firstchat.entities.InitPayload;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.entities.SocketRequestContainer;
import com.kalmahik.firstchat.storage.UserPreferences;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



public class WSClient {
    private static final String URL = "ws://62.109.16.33:8083/";
    private static WSClient instance = new WSClient();
    private WebSocketClient wsClient;
    private Gson gson = new Gson();
    private List<MessageReceiver> observers = new CopyOnWriteArrayList<>();
    private Message message;
    //private UserPreferences preferences = new UserPreferences(this);

    private WSClient() {
    }

    public static WSClient getInstance() {
        return instance;
    }

    public void connect() {
        if (!isConnected()) {
            try {
                wsClient = new WebSocketClient(new URI(URL)) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        SocketRequestContainer request =
                                new SocketRequestContainer(new InitPayload("4f4fd644-c013-4ebf-82e0-0d195124b8d212e56d7a-11b3-481e-a743-75f2b370c2be"));
                        send(gson.toJson(request));
                    }

                    @Override
                    public void onMessage(String messageStr) {
                        Log.d(WSClient.class.getSimpleName(), "received: " + message);
                        //Лучше парсить в Message и отправлять подписчикам распарсенный объект
                        Gson gson = new Gson();
                        message = gson.fromJson(messageStr, Message.class);
                        for (MessageReceiver o : observers) {
                            o.onMessageReceived(message);
                        }
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        wsClient = null;
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.e(WSClient.class.getSimpleName(), Log.getStackTraceString(ex));
                    }
                };
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerObserver(MessageReceiver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(MessageReceiver observer) {
        observers.remove(observer);
    }

    public void disconnect() {
        if (wsClient.getConnection().isOpen()) {
            wsClient.close();
            wsClient = null;
        }
    }


    public boolean isConnected() {
        if (wsClient == null || wsClient.getConnection().isClosed()) {
            return false;
        }
        return true;
    }
}