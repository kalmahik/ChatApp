package com.kalmahik.firstchat.entities;


import com.kalmahik.firstchat.util.DateUtil;

import java.util.UUID;

public class RequestContainer<T> {
    private String token;
    private String requestId;
    private long ts;
    private T payload;

    public RequestContainer() {
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
    }

    public RequestContainer(T payload) {
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
        this.payload = payload;
    }
}