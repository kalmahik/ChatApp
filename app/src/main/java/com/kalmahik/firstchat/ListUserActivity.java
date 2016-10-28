package com.kalmahik.firstchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private ListUserAdapter adapter;
	private ArrayList<User> users;

	private OnListItemClickListener clickListener = new OnListItemClickListener() {
		@Override
		public void onClick(View v, int position) {
			//Toast.makeText(ListUserActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ListUserActivity.this, ListMessageActivity.class);
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

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.attachToRecyclerView(recyclerView);

		users = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			users.add(new User(i + "Username", i + "Description", i + "Image"));
		}

		adapter = new ListUserAdapter(users, clickListener);
		recyclerView.setAdapter(adapter);
	}

	public void onListChanged(int position) {
		adapter.notifyDataSetChanged();
	}
}
