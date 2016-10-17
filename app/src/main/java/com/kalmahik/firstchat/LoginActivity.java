package com.kalmahik.firstchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView registerRef;
    private Button signInButton;
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

        signInButton = (Button) findViewById(R.id.button_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        logo = (TextView) findViewById(R.id.logo);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctSignIn();
            }
        });

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setText("@firstchat");
        logo.setTypeface(typeFace);
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
