package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kalmahik.firstchat.entities.Chat;
import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.ChatListAdapter;
import com.kalmahik.firstchat.storage.ChatDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ChatListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<Chat> chats;
    private FloatingActionButton fab;
    private ChatDatabase chatDB;
    private Chat chat;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(ChatListActivity.this, MessageListActivity.class);
            intent.putExtra("title", chats.get(position).getTitle());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chats);
        chatDB = new ChatDatabase();
        chats = chatDB.getAll();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        createFakeUsers();
        performUsers();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        chats = new ArrayList<>();

//        for (int i = 0; i < 40; i++) {
//            chats.add(new Chat(UUID.randomUUID().toString(), i + "Title", i + "Last message", i + 24101976));
//        }

//        Collections.sort(chats, new Comparator<Chat>() {
//            public int compare(Chat chat1, Chat chat2) {
//                String s1 = Long.toString(chat1.getUpdated());
//                String s2 = Long.toString(chat2.getUpdated());
//                return s2.compareTo(s1);
//            }
//        });

        adapter = new ChatListAdapter(chats, clickListener);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

    }

    public void createFakeUsers() {
        chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chat = new Chat(UUID.randomUUID().toString(), i + "Username", i + "Description", 2016-i);
            chats.add(chat);
            chatDB.copyOrUpdate(chat);
        }
    }

    private void performUsers() {
        chats = chatDB.getAll();
        chatDB.addChangeListener(element -> {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onListChanged(int position) {
        adapter.notifyDataSetChanged();
    }
}
