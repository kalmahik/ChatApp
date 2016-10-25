package com.kalmahik.firstchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListContactActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private ListContactAdapter adapter;
	private ArrayList<User> users;

	private OnListItemClickListener clickListener = new OnListItemClickListener() {
		@Override
		public void onClick(View v, int position) {
			Toast.makeText(ListContactActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_contact);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		users = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			users.add(new User(i+ "Username ", "Status" + i));
		}

		adapter = new ListContactAdapter(users, clickListener);
		recyclerView.setAdapter(adapter);
	}

	public void onListChanged(int position) {
		adapter.notifyDataSetChanged();
	}
}
