package com.example.pat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class register extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private Spinner jenis_mobilField;
    private Button registerButton;

    private static final String REGISTER_URL = "http://192.168.146.73:7000/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        jenis_mobilField = findViewById(R.id.jenis_mobilField);
        registerButton = findViewById(R.id.registerField);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent intent = new Intent(register.this, mainmenu.class);
                startActivity(intent);
            }
        });

        // Daftar pilihan jenis mobil
        String[] jenismobilItems = {"Sedan", "Hatchback", "Coupe", "MPV", "SUV", "Crossover", "Minivan", "Konvertibel", "Sportscar", "Mobil Listrik"};
        ArrayAdapter<String> jenismobilAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jenismobilItems);
        jenismobilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenis_mobilField.setAdapter(jenismobilAdapter);

        // Mengatur listener untuk item yang dipilih pada spinner jenis mobil
        jenis_mobilField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJenisMobil = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Jenis Mobil terpilih: " + selectedJenisMobil, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Kosongkan
            }
        });
    }

    private void registerUser() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String jenis_mobil = jenis_mobilField.getSelectedItem().toString().trim();

        // Perform your validations here
        if (username.isEmpty() || password.isEmpty() || jenis_mobil.isEmpty()) {
            Toast.makeText(register.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON request body with the user data
        String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"jenis_mobil\":\"" + jenis_mobil + "\"}";

        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Create a request with the request body
        Request request = new Request.Builder()
                .url(REGISTER_URL)
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
                    boolean isRegisterSuccess = json.getBoolean("isRegisterSuccess");
                    String message = json.getString("message");

                    if (isRegisterSuccess) {
                        // Move to the main menu activity
                        Intent intent = new Intent(register.this, mainmenu.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Display the error message
                        runOnUiThread(() -> Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String requestBody = params[0];
            String response = "";

            try {
                URL url = new URL(REGISTER_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Write the request body to the connection's output stream
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(requestBody);
                writer.flush();
                writer.close();

                // Get the response from the server
                int responseCode = connection.getResponseCode();
                Log.d("konsol:", writer.toString());

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    response = "Registration successful";
                    Intent intent = new Intent(register.this, mainmenu.class);
                    startActivity(intent);
                    finish();
                } else {
                    response = "Registration failed";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(register.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}