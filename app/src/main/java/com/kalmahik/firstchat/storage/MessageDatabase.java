package com.kalmahik.firstchat.storage;


import com.kalmahik.firstchat.entities.Message;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class MessageDatabase{
    private Realm realm;

    public MessageDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("message_db")
                .build();
        realm = Realm.getInstance(configuration);
    }

    public void copyOrUpdate(Message message) {
        realm.beginTransaction();
		realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
    }

    public void copyOrUpdate(List<Message> message) {
        realm.beginTransaction();
		realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
    }

    public List<Message> getAll() {
		return realm.where(Message.class).findAllSorted("created", Sort.ASCENDING);
    }

    public List<Message> getAllByChatId(String chatId){
        return realm.where(Message.class)
                .equalTo("chatId", chatId)
                .findAll();
    }

    public void erase() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void addChangeListener(RealmChangeListener<Realm> changeListener) {
        realm.addChangeListener(changeListener);
    }
}
