package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kalmahik.firstchat.R;

public class LoginActivity extends AppCompatActivity {

    private TextView registerRef;
    private TextView recoveryRef;
    private Button loginButton;
    private EditText username;
    private EditText password;
    private TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerRef = (TextView) findViewById(R.id.ref_register);

        registerRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        recoveryRef = (TextView) findViewById(R.id.ref_recovery);

        recoveryRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        loginButton = (Button) findViewById(R.id.button_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        logo = (TextView) findViewById(R.id.logo);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctSignIn();
            }
        });

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setTypeface(typeFace);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void correctSignIn() {
        if (username.length() > 0) {
            if (password.length() > 0) {
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
