package com.kalmahik.firstchat;

public class Chat {
    private String id;
    //
    private String title;
    private String author;
    private String[] participants;
    //
    private String lastMessage;
    private long created;
    //
    private long updated;

    public Chat(String title, String lastMessage, long updated) {
        this.lastMessage = lastMessage;
        this.title = title;
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
