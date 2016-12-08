package com.kalmahik.firstchat.entities;

public class MessageSendPayload {
    private String chatId;
    private String body;

    public MessageSendPayload(String chatId, String body) {
        this.chatId = chatId;
        this.body = body;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
