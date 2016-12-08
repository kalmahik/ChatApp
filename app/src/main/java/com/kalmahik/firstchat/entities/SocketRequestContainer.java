package com.kalmahik.firstchat.entities;


import com.kalmahik.firstchat.util.DateUtil;

import java.util.UUID;

public class SocketRequestContainer {
    private String requestId;
    private long ts;
    private InitPayload payload;
    private String method = "initConnection";

    public SocketRequestContainer(InitPayload payload) {
        requestId = UUID.randomUUID().toString();
        ts = DateUtil.now();
        this.payload = payload;
    }
}
