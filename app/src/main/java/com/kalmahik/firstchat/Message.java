package com.kalmahik.firstchat;

public class Message {
    private String id;
    //
    private String sender;
    private String chatId;
    //
    private long created;
    //
    private String body;

    public Message(String sender, long created, String body) {
        this.sender = sender;
        this.created = created;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public long getCreated() {
        return created;
    }

    public String getBody() {
        return body;
    }
}
