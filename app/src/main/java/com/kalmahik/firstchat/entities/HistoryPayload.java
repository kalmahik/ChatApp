package com.kalmahik.firstchat.entities;

public class HistoryPayload {
    private String chatId;
    private long sience;
    private int  limit;



    public HistoryPayload(String chatId, long sience, int limit) {
        this.chatId = chatId;
        this.sience = sience;
        this.limit = limit;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public long getSience() {
        return sience;
    }

    public void setSience(long sience) {
        this.sience = sience;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
