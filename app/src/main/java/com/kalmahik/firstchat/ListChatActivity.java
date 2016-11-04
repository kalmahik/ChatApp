package com.kalmahik.firstchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListChatAdapter adapter;
    private ArrayList<Chat> chats;
    private FloatingActionButton fab;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(ListChatActivity.this, ListMessageActivity.class);
            intent.putExtra("title", chats.get(position).getTitle());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chats);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = (FloatingActionButton)findViewById(R.id.fab);

        chats = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            chats.add(new Chat(i + "Title", i + "Last message", i + 24101976));
        }

        Collections.sort(chats, new Comparator<Chat>() {
            public int compare(Chat chat1, Chat chat2) {
                String s1 = Long.toString(chat1.getUpdated());
                String s2 = Long.toString(chat2.getUpdated());
                return s2.compareTo(s1);
            }
        });

        adapter = new ListChatAdapter(chats, clickListener);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListChatActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onListChanged(int position) {
        adapter.notifyDataSetChanged();
    }
}
