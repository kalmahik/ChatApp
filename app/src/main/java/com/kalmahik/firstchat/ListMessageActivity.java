package com.kalmahik.firstchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListMessageAdapter adapter;
    private ArrayList<Message> messages;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Toast.makeText(ListMessageActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            messages.add(new Message(i%2+"",1976+i, "My message" + i));
        }

        adapter = new ListMessageAdapter(messages, clickListener);
        recyclerView.setAdapter(adapter);
    }

    public void onListChanged(int position) {
        adapter.notifyDataSetChanged();
    }
}
