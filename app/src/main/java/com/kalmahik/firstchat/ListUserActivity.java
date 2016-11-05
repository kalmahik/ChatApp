package com.kalmahik.firstchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.kalmahik.firstchat.storage.UserDatabase;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;



public class ListUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListUserAdapter adapter;
    private List<User> users;
    private ArrayList<User> newUsers;
    private UserDatabase userDB;


    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(ListUserActivity.this, ListMessageActivity.class);
            intent.putExtra("title", users.get(position).getName());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        userDB = new UserDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        createFakeUsers();
        performUsers();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);


        adapter = new ListUserAdapter(users, clickListener);
        recyclerView.setAdapter(adapter);


    }


    public void createFakeUsers() {

        users = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            users.add(new User(i + "Username", i + "Description", i + "Image"));
        }
        userDB.copyOrUpdate(users);

    }

    private void performUsers() {
        //users = userDB.getAll();
        userDB.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
