package com.kalmahik.firstchat.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kalmahik.firstchat.api.ChatApi;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.storage.UserPreferences;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText password;
    private EditText passwordConfirm;
    private EditText username;
    private TextView logo;
    private UserPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences = new UserPreferences(this);

        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.confirm_password);
        username = (EditText) findViewById(R.id.username);
        logo = (TextView) findViewById(R.id.logo);
        registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(v -> correctRegister());

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setTypeface(typeFace);
    }

    private void correctRegister() {
        if (username.length() > 0) {
            if (password.length() > 7) {
                if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    doSignUp();
                } else {
                    Toast.makeText(this, "Passwords different", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Passwords length less 8 chapters", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
        }
    }


    private void doSignUp() {
        ChatApi.getInstance().register(username.getText().toString(), password.getText().toString(),
                result -> {
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
                    preferences.setToken(result.getToken());
                    preferences.setSelfId(result.getUser().getId());
                    preferences.setUsername(result.getUser().getName());
                },
                result -> Toast.makeText(RegisterActivity.this, "Error register", Toast.LENGTH_SHORT).show());
    }
}