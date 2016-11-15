package com.kalmahik.firstchat.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kalmahik.firstchat.R;
import com.kalmahik.firstchat.entities.AuthPayload;
import com.kalmahik.firstchat.entities.AuthResponse;
import com.kalmahik.firstchat.entities.RequestContainer;
import com.kalmahik.firstchat.entities.ResponseContainer;
import com.kalmahik.firstchat.util.HashUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        registerRef.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        recoveryRef = (TextView) findViewById(R.id.ref_recovery);

        recoveryRef.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        loginButton = (Button) findViewById(R.id.button_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        logo = (TextView) findViewById(R.id.logo);

        loginButton.setOnClickListener(v -> correctSignIn());

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/twiddlestix.ttf");
        logo.setTypeface(typeFace);

        logo.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
            startActivity(intent);
        });
    }

    private void correctSignIn() {
        if (username.length() > 0) {
            if (password.length() > 0) {
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                doSignUp();
            } else {
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void doSignUp() {
        AuthPayload payload = new AuthPayload(username.getText().toString(), HashUtil.hash(password.getText().toString()));
        final RequestContainer<AuthPayload> requestContainer = new RequestContainer<>(payload);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody
                        .create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestContainer));
                Request request = new Request.Builder()
                        .url("http://91.122.56.48:8084/levelupchat/auth")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    ResponseContainer rc = gson.fromJson(response.body().string(), ResponseContainer.class);
                    AuthResponse ar = gson.fromJson(rc.getPayload().toString(), AuthResponse.class);
                    Log.d(LoginActivity.class.getSimpleName(), ar.getUser().getName());

                } catch (IOException e) {
                    Log.e(LoginActivity.class.getSimpleName(), Log.getStackTraceString(e));
                }
//				try {
//					URL url = new URL("http://91.122.56.48:8080/levelupchat/register");
//					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//					connection.setRequestMethod("POST");
//					connection.setDoInput(true);
//					connection.setDoOutput(true);
//					connection.setRequestProperty("Content-Length", String.valueOf(hello.getBytes().length));
//
//					OutputStream os = connection.getOutputStream();
//					os.write(hello.getBytes("UTF-8"));
//					connection.connect();
//
//					int responseCode = connection.getResponseCode();
//
//					InputStream is = connection.getInputStream();
//					ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
//					byte[] buffer = new byte[4096];
//					int length;
//					while ((length = is.read(buffer)) != -1) {
//						byteArrayStream.write(buffer, 0, length);
//					}
//					String response = byteArrayStream.toString();
//					Log.d(RegisterActivity.class.getSimpleName(), "RESPONSE code: " + responseCode + " body: " + response);
//					is.close();
//				} catch (IOException e) {
//					Log.e(RegisterActivity.class.getSimpleName(), Log.getStackTraceString(e));
//				}
            }
        }).start();
    }
}
