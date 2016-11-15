package com.kalmahik.firstchat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kalmahik.firstchat.OnListItemClickListener;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.adapters.MessageListAdapter;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.storage.MessageDatabase;

import java.util.List;
import java.util.UUID;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Message> messages;
    private Button sendButton;
    private EditText textInput;
    private MessageDatabase messageDB;
    private Message newMessage;

    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Toast.makeText(MessageListActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitle(getIntent().getExtras().getString("title"));

        messageDB = new MessageDatabase();
        messages = messageDB.getAll();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(messages, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

        textInput = (EditText) findViewById(R.id.input_text);

        sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMessage = new Message(UUID.randomUUID().toString(), 0 + "Sender", 1976, textInput.getText().toString());
                messageDB.copyOrUpdate(newMessage);
                onListChanged();
                textInput.setText("");
            }
        });

    }
    public void onListChanged() {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
    }


}
