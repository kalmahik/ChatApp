package com.kalmahik.firstchat.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {
    @PrimaryKey
    private String id;
    private String sender;
    private String chatId;
    private long created;
    private String body;

    public Message() {

    }

    public Message(String id, String sender, long created, String body) {
        this.id = id;
        this.sender = sender;
        this.created = created;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
