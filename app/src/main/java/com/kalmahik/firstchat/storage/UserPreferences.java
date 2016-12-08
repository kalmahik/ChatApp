package com.kalmahik.firstchat.storage;


import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
	public static final String KEY_TOKEN = "key_token";
	public static final String KEY_SELF_ID = "key_self_id";
	public static final String KEY_USERNAME = "key_username";
	private SharedPreferences preferences;

	public UserPreferences(Context context) {
		preferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
	}

	public void setToken(String token) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(KEY_TOKEN, token);
		editor.apply();
	}

	public String getToken() {
		return preferences.getString(KEY_TOKEN, null);
	}

	public void setSelfId(String id) {
		preferences.edit()
				.putString(KEY_SELF_ID, id)
				.apply();
	}

	public String getSelfId() {
		return preferences.getString(KEY_SELF_ID, null);
	}

	public void setUsername(String username) {
		preferences.edit()
				.putString(KEY_USERNAME, username)
				.apply();
	}

	public String getUsername() {
		return preferences.getString(KEY_USERNAME, null);
	}
}
