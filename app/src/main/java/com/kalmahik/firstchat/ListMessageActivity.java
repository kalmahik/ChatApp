package com.kalmahik.firstchat;

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

import com.kalmahik.firstchat.storage.MessageDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListMessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListMessageAdapter adapter;
    private List<Message> messages;
    private Button sendButton;
    private EditText textInput;
    private MessageDatabase messageDB;
    private Message newMessage;


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

        messages = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            messages.add(new Message(i % 2 + "Sender", i + 1976, i + "My message"));
        }


        sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInput = (EditText) findViewById(R.id.input_text);
                newMessage = new Message(0 + "Sender", 1976, textInput.getText().toString());
                messages.add(newMessage);
                messageDB.copyOrUpdate(newMessage);
                onListChanged(messages.size() - 1);
                textInput.setText("");
            }
        });


        adapter = new ListMessageAdapter(messages, clickListener);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

    }

    public void onListChanged(int position) {
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        adapter.notifyDataSetChanged();
    }


}
