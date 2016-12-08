package com.kalmahik.firstchat.entities;


import com.kalmahik.firstchat.storage.UserPreferences;
import com.kalmahik.firstchat.util.DateUtil;

import java.util.UUID;

public class RequestContainer<T> {
    private String token;
    private String requestId;
    private long ts;
    private T payload;
    private UserPreferences preferences;

    public RequestContainer(String token) {
        this.token = token;
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
    }

    public RequestContainer(String token, T payload) {
        this.token = token;
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
        this.payload = payload;
    }

    public RequestContainer(T payload) {
        this.token = token;
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
        this.payload = payload;
    }
}