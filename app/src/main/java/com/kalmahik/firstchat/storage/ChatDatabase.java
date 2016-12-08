package com.kalmahik.firstchat.storage;


import com.kalmahik.firstchat.entities.Chat;
import com.kalmahik.firstchat.entities.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class ChatDatabase {
    private Realm realm;

    public ChatDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("chat_db")
                .build();
        realm = Realm.getInstance(configuration);
    }

    public void copyOrUpdate(Chat chat) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(chat);
        realm.commitTransaction();
    }

    public void copyOrUpdate(List<Chat> chats) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(chats);
        realm.commitTransaction();
    }

    public List<Chat> getAll() {
        return realm.where(Chat.class).findAllSorted("lastMessage", Sort.DESCENDING);
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void erase() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void addChangeListener(RealmChangeListener<Realm> changeListener) {
        realm.addChangeListener(changeListener);
    }
}
