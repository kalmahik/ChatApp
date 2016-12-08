package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kalmahik.firstchat.api.ChatApi;
import com.kalmahik.firstchat.api.MessageReceiver;
import com.kalmahik.firstchat.api.WSClient;
import com.kalmahik.firstchat.entities.Chat;
import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.ChatListAdapter;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.services.MessageService;
import com.kalmahik.firstchat.storage.ChatDatabase;
import com.kalmahik.firstchat.storage.UserPreferences;

import java.util.List;

public class ChatListActivity extends AppCompatActivity
        implements MessageReceiver {
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<Chat> chats;
    private FloatingActionButton fab;
    private ChatDatabase chatDB;


    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(ChatListActivity.this, MessageListActivity.class);
            intent.putExtra("title", chats.get(position).getTitle());
            intent.putExtra("id", chats.get(position).getParticipants());
            intent.putExtra("chatId", chats.get(position).getId());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chats);

        startService(new Intent(this, MessageService.class));

        //при логауте
//		stopService(new Intent(this, MessageService.class));

        chatDB = new ChatDatabase();
        getChats();
        performChats();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new ChatListAdapter(chats, clickListener);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ChatListActivity.this, UserListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        WSClient.getInstance().registerObserver(this);
    }

    @Override
    protected void onStop() {
        WSClient.getInstance().unregisterObserver(this);
        super.onStop();

    }

    private void performChats() {
        chats = chatDB.getAll();
        chatDB.addChangeListener(element -> {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getChats() {
        UserPreferences preferences = new UserPreferences(this);
        ChatApi.getInstance().getChats(preferences.getToken(),
                result -> {
                    if (result.size() > 0)
                        chatDB.copyOrUpdate(result);
                },
                result -> Toast.makeText(this, "Error get chats", Toast.LENGTH_SHORT).show());
    }

    public void onListChanged() {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(ChatListActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageReceived(Message message) {

    }
    
}
