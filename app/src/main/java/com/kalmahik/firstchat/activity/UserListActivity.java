package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.UserListAdapter;
import com.kalmahik.firstchat.entities.User;
import com.kalmahik.firstchat.storage.UserDatabase;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserListAdapter adapter;
    private List<User> users;
    private UserDatabase userDB;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(UserListActivity.this, MessageListActivity.class);
            intent.putExtra("title", users.get(position).getName());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        userDB = new UserDatabase();
        //users = userDB.getAll();

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        adapter = new UserListAdapter(users, clickListener);
        recyclerView.setAdapter(adapter);
    }

    public void createFakeUsers() {
        users = new ArrayList<>();
        users.add(new User(UUID.randomUUID().toString(), "sveta95",  "Всем привет!)", "Image"));
        users.add(new User(UUID.randomUUID().toString(), "belova39",  "Все что не делается - все к лучшему", "Image"));
        users.add(new User(UUID.randomUUID().toString(), "ivan.ivan",  "Тут", "Image"));
        users.add(new User(UUID.randomUUID().toString(), "egor.petrov", "Доступен", "Image"));
        userDB.copyOrUpdate(users);
    }

    private void performUsers() {
        users = userDB.getAll();
        userDB.addChangeListener(element -> {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
