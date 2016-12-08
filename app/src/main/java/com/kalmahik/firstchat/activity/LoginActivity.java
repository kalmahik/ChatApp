package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.api.ChatApi;
import com.kalmahik.firstchat.storage.UserPreferences;

public class LoginActivity extends AppCompatActivity {

    private TextView registerRef;
    private TextView recoveryRef;
    private Button loginButton;
    private EditText username;
    private EditText password;
    private TextView logo;
    private UserPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = new UserPreferences(this);

        if (preferences.getToken() != null) {
            Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
            startActivity(intent);
        }

        recoveryRef = (TextView) findViewById(R.id.ref_recovery);
        registerRef = (TextView) findViewById(R.id.ref_register);
        loginButton = (Button) findViewById(R.id.button_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        logo = (TextView) findViewById(R.id.logo);

        registerRef.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        logo.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> correctSignIn());

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setTypeface(typeFace);
    }

    private void correctSignIn() {
        if (username.length() > 0) {
            if (password.length() > 0) {
                doSignUp();
            } else {
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void doSignUp() {
        ChatApi.getInstance().auth(username.getText().toString(), password.getText().toString(),
                result -> {
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                    preferences.setToken(result.getToken());
                    preferences.setSelfId(result.getUser().getId());
                    preferences.setUsername(result.getUser().getName());

                    Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
                    startActivity(intent);
                },
                result -> Toast.makeText(LoginActivity.this, "Error login", Toast.LENGTH_SHORT).show());
    }
}
