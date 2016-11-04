package com.kalmahik.firstchat;

public class Chat {
    private String id;
    private String title;
    private String[] participants;
    private String lastMessage;
    private long created;
    private long updated;

    public Chat(){

    }

    public Chat(String title, String lastMessage, long updated) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.updated = updated;
    }

    public long getUpdated() {
        return updated;
    }

    public String getTitle() {
        return title;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
