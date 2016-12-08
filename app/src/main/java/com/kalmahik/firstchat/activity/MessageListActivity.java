package com.kalmahik.firstchat.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.MessageListAdapter;
import com.kalmahik.firstchat.api.ChatApi;
import com.kalmahik.firstchat.api.MessageReceiver;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.storage.MessageDatabase;
import com.kalmahik.firstchat.storage.UserPreferences;
import com.kalmahik.firstchat.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity implements MessageReceiver{
    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Message> messages;
    private Button sendButton;
    private EditText textInput;
    private MessageDatabase messageDB;
    private Message newMessage;
    private String chatId;
    private UserPreferences preferences;


    private OnListItemClickListener clickListener = (v, position) ->
            Toast.makeText(MessageListActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        preferences = new UserPreferences(this);
        System.out.println(preferences.getToken());
        doCreateChat();
        doGetMessages(getIntent().getExtras().getString("chatId"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitle(getIntent().getExtras().getString("title"));

        messages = new ArrayList<>();
        messageDB = new MessageDatabase();
        messages = messageDB.getAllByChatId(getIntent().getExtras().getString("chatId"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(messages, clickListener, preferences);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

        textInput = (EditText) findViewById(R.id.input_text);

        sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(v -> {
            doSendMessage(getIntent().getExtras().getString("chatId"));
            textInput.setText("");
            doGetMessages(getIntent().getExtras().getString("chatId"));
            onListChanged();
        });
    }

    public void onListChanged() {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
    }

    public void doCreateChat() {
        ChatApi.getInstance().doCreateChat(preferences.getToken(), getIntent().getExtras().getString("id"),
                result -> {
                    Toast.makeText(this, "Chat create success", Toast.LENGTH_SHORT).show();
                    getCHatId(result.getId());
                }, result -> {
                    Toast.makeText(MessageListActivity.this, "Error create chat", Toast.LENGTH_SHORT).show();
                });
    }

    public void getCHatId(String chatId) {
        this.chatId = chatId;

    }

    public void doGetMessages(String chatId) {
        ChatApi.getInstance().getMessages(preferences.getToken(), chatId, DateUtil.now(), 10,
                result -> {
                    messageDB.copyOrUpdate(result);
                    Log.d(MessageListActivity.class.getSimpleName(), "History get success");
                }, result -> {
                    Toast.makeText(MessageListActivity.this, "Error get History", Toast.LENGTH_SHORT).show();
                });
    }

    public void doSendMessage(String chatId) {
        ChatApi.getInstance().doSendMessage(preferences.getToken(), chatId, textInput.getText().toString(),
                result -> {
                    messageDB.copyOrUpdate(result);
                    onListChanged();
                    Log.d(MessageListActivity.class.getSimpleName(), "Message send success");
                }, result -> {
                    Toast.makeText(MessageListActivity.this, "Error send message", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onMessageReceived(Message message) {
        System.out.println(message.getBody());
        onListChanged();

    }
}
