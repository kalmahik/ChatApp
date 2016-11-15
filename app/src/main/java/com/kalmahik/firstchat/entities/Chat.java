package com.kalmahik.firstchat.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Chat extends RealmObject{
    @PrimaryKey
    private String id;
    private String title;
    private String participant;
    private String lastMessage;
    private long created;
    private long updated;

    public Chat() {

    }

    public Chat(String id, String title, String lastMessage, long updated) {
        this.id = id;
        this.title = title;
        this.lastMessage = lastMessage;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParticipants() {
        return participant;
    }

    public void setParticipants(String participants) {
        this.participant = participants;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
