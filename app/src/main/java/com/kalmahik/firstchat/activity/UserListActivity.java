package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.UserListAdapter;
import com.kalmahik.firstchat.api.ChatApi;
import com.kalmahik.firstchat.entities.User;
import com.kalmahik.firstchat.storage.UserDatabase;
import com.kalmahik.firstchat.storage.UserPreferences;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

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
            intent.putExtra("id", users.get(position).getId());
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
        getUsers();
        performUsers();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        adapter = new UserListAdapter(users, clickListener);
        recyclerView.setAdapter(adapter);
    }


    private void performUsers() {
        users = userDB.getAll();
        userDB.addChangeListener(element -> {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getUsers() {
        UserPreferences preferences = new UserPreferences(this);
        ChatApi.getInstance().getUsers(preferences.getToken(),
                result -> {
                    userDB.copyOrUpdate(result);
                    System.out.println(result.get(2).getImage());
                    Toast.makeText(UserListActivity.this, "Success get UserList", Toast.LENGTH_SHORT).show();
                },
                result -> Log.e(UserListActivity.class.getSimpleName(), "Error getting UserList"));

    }

}
