package com.kalmahik.firstchat.api;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.kalmahik.firstchat.Func;
import com.kalmahik.firstchat.activity.UserListActivity;
import com.kalmahik.firstchat.entities.AuthPayload;
import com.kalmahik.firstchat.entities.AuthResponse;
import com.kalmahik.firstchat.entities.Chat;
import com.kalmahik.firstchat.entities.HistoryPayload;
import com.kalmahik.firstchat.entities.Message;
import com.kalmahik.firstchat.entities.MessageSendPayload;
import com.kalmahik.firstchat.entities.RequestContainer;
import com.kalmahik.firstchat.entities.ResponseContainer;
import com.kalmahik.firstchat.entities.User;
import com.kalmahik.firstchat.util.HashUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatApi {
    private static final String BASE_URL = "http://62.109.16.33:8084/levelupchat";
    private static final ChatApi instance = new ChatApi();
    private OkHttpClient client;
    private Gson gson;
    private Handler uiHandler;

    private ChatApi() {
        client = new OkHttpClient();
        gson = new Gson();
        uiHandler = new Handler(Looper.getMainLooper());
    }

    public static ChatApi getInstance() {
        return instance;
    }

    private Request prepareRequest(RequestContainer requestContainer, String urlSuffix) {
        RequestBody body = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(requestContainer));
        Request request = new Request.Builder()
                .url(BASE_URL + urlSuffix)
                .post(body)
                .build();
        return request;
    }

    public void register(String name, String pwd, Func<AuthResponse> onSuccess,
                         Func<Throwable> onError) {
        doRegOrAuth("/register", name, pwd, onSuccess, onError);
    }

    public void auth(String name, String pwd, Func<AuthResponse> onSuccess,
                     Func<Throwable> onError) {
        doRegOrAuth("/auth", name, pwd, onSuccess, onError);
    }

    private void doRegOrAuth(final String urlSuffix, final String name, final String pwd, final Func<AuthResponse> onSuccess,
                             final Func<Throwable> onError) {
        new Thread(() -> {
            AuthPayload payload = new AuthPayload(name, HashUtil.hash(pwd));
            RequestContainer<AuthPayload> requestContainer = new RequestContainer<>(payload);
            Request request = prepareRequest(requestContainer, urlSuffix);
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<AuthResponse> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<AuthResponse>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }

    public void getUsers(String token, final Func<List<User>> onSuccess, final Func<Throwable> onError) {
        new Thread(() -> {
            RequestContainer requestContainer = new RequestContainer(token);
            Request request = prepareRequest(requestContainer, "/users/getAll");
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<List<User>> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<ArrayList<User>>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }

    public void getChats(String token, final Func<List<Chat>> onSuccess, final Func<Throwable> onError) {
        new Thread(() -> {
            RequestContainer requestContainer = new RequestContainer(token);
            Request request = prepareRequest(requestContainer, "/chat/getAll");
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<List<Chat>> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<ArrayList<Chat>>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }


    public void doSendMessage(String token, final String chatId, final String body, final Func<Message> onSuccess,
                              final Func<Throwable> onError) {
        new Thread(() -> {
            MessageSendPayload payload = new MessageSendPayload(chatId, body);
            RequestContainer<MessageSendPayload> requestContainer = new RequestContainer<>(token, payload);
            Request request = prepareRequest(requestContainer, "/chat/message/send");
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<Message> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<Message>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body1 = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body1)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }


    public void doCreateChat(String token, final String participant, final Func<Chat> onSuccess,
                             final Func<Throwable> onError) {
        new Thread(() -> {
            RequestContainer<String> requestContainer = new RequestContainer<>(token, participant);
            Request request = prepareRequest(requestContainer, "/chat/create");
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<Chat> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<Chat>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }

    public void getMessages(String token, String chatId, long sience, final int limit, final Func<List<Message>> onSuccess, final Func<Throwable> onError) {
        new Thread(() -> {
            HistoryPayload payload = new HistoryPayload(chatId, sience, limit);
            RequestContainer<HistoryPayload> requestContainer = new RequestContainer<>(token, payload);
            Request request = prepareRequest(requestContainer, "/chat/message/history");
            try {
                final Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String json = response.body().string();
                    final ResponseContainer<List<Message>> responseContainer =
                            gson.fromJson(json, new TypeToken<ResponseContainer<ArrayList<Message>>>() {
                            }.getType());
                    uiHandler.post(() -> onSuccess.onResult(responseContainer.getPayload()));
                } else {
                    final String body = response.body().string();
                    uiHandler.post(() -> onError.onResult(new IOException("Bad response code: " + response.code() +
                            " reason: " + body)));
                }
            } catch (final IOException e) {
                uiHandler.post(() -> onError.onResult(e));
            }
        }).start();
    }
}
