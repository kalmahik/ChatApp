package com.kalmahik.firstchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kalmahik.firstchat.storage.UserPreferences;

public class ProfileActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		UserPreferences preferences = new UserPreferences(this);
		preferences.setToken("dfjkgdfkgkdfg");
		preferences.getToken();
	}
}
