package com.example.pat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {

        private EditText usernameField;
        private EditText passwordField;
        private Button loginButton;
        private Button backButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            usernameField = findViewById(R.id.usernameField);
            passwordField = findViewById(R.id.passwordField);
            loginButton = findViewById(R.id.loginButton);
            backButton = findViewById(R.id.backButton);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });
        }

    private void registerUser() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Perform your validations here
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(login.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON request body with the user data
        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("username", username);
            requestBodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = requestBodyJson.toString();

        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Create a request with the request body
        Request request = new Request.Builder()
                .url("http://192.168.146.73:7000/logincustomer")
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the failure case
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response
                String responseBody = response.body().string();

                // Parse the JSON response
                try {
                    JSONObject json = new JSONObject(responseBody);
                    boolean usernameExists = json.getBoolean("username_ada");
                    String message = json.getString("message");

                    if (usernameExists) {
                        // Move to the main menu activity
                        Intent intent = new Intent(login.this, mainmenu.class);
                        startActivity(intent);
                    } else {
                        // Display the error message
                        runOnUiThread(() -> Toast.makeText(login.this, "Login Gagal!", Toast.LENGTH_SHORT).show());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}