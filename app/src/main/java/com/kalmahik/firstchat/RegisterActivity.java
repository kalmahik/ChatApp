package com.kalmahik.firstchat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText password;
    private EditText confirmPswd;
    private EditText username;
    private TextView logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();

        password = (EditText) findViewById(R.id.password);
        confirmPswd = (EditText) findViewById(R.id.confirm_password);
        username = (EditText) findViewById(R.id.username);
        logo = (TextView) findViewById(R.id.logo);

        registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctRegister();
            }
        });

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setTypeface(typeFace);
    }

    private void correctRegister() {
        if (username.length() > 0) {
            if (password.length() > 7) {
                if (password.getText().toString().equals(confirmPswd.getText().toString())) {
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Passwords different", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Passwords length less 8 chapters", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
        }
    }
}