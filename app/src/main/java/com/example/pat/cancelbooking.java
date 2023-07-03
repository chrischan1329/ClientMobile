package com.example.pat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class cancelbooking extends AppCompatActivity {

    private EditText tanggalField;
    private EditText waktuField;
    private EditText usernameField;
    private Spinner jenis_mobilField;
    private Spinner jenis_layananField;
    private Spinner lokasiField;
    private Button cancel_bookingButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbooking);

        tanggalField = findViewById(R.id.tanggalField);
        waktuField = findViewById(R.id.waktuField);
        usernameField = findViewById(R.id.usernameField);
        jenis_mobilField = findViewById(R.id.jenis_mobilField);
        jenis_layananField = findViewById(R.id.jenis_layananField);
        lokasiField = findViewById(R.id.lokasiField);
        cancel_bookingButton = findViewById(R.id.cancel_bookingField);
        backButton = findViewById(R.id.backButton);

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

        // Daftar pilihan jenis layanan
        String[] jenislayananItems = {"Cuci Mobil Standar", "Cuci Mobil Interior", "Cuci Mobil Eksterior", "Detailing Mobil", "Waxing & Polishing", "Cuci Mesin", "Poles Lampu"};
        ArrayAdapter<String> jenislayananAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jenislayananItems);
        jenislayananAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenis_layananField.setAdapter(jenislayananAdapter);

        // Mengatur listener untuk item yang dipilih pada spinner jenis layanan
        jenis_layananField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJenisLayanan = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Jenis Layanan terpilih: " + selectedJenisLayanan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Kosongkan
            }
        });

        // Daftar pilihan lokasi
        String[] lokasiItems = {"Siwalankerto", "Kutisari", "Jemursari"};
        ArrayAdapter<String> LokasiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lokasiItems);
        LokasiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiField.setAdapter(LokasiAdapter);

        // Mengatur listener untuk item yang dipilih pada spinner lokasi
        lokasiField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLokasi = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Lokasi terpilih: " + selectedLokasi, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Kosongkan
            }
        });

        cancel_bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal = tanggalField.getText().toString();
                String waktu = waktuField.getText().toString();
                String username = usernameField.getText().toString();
                String jenis_mobil = jenis_mobilField.getSelectedItem().toString();
                String jenis_layanan = jenis_layananField.getSelectedItem().toString();
                String lokasi = lokasiField.getSelectedItem().toString();

                if (username.isEmpty() || jenis_mobil.isEmpty() || jenis_layanan.isEmpty() || lokasi.isEmpty()) {
                    Toast.makeText(cancelbooking.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Create the JSON object for cancelbooking data
                        JSONObject cancelData = new JSONObject();
                        cancelData.put("username", username);
                        cancelData.put("jenis_mobil", jenis_mobil);

                        // Send the cancelbooking data to the server
                        sendCancelRequest(cancelData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void sendCancelRequest(final JSONObject cancelData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.22.70.22:7000/cancelbooking"); // Replace with your server URL

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Write the cancelbooking data to the request body
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.write(cancelData.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Get the response from the server
                    int responseCode = connection.getResponseCode();
                    String responseMessage = connection.getResponseMessage();
                    final String responseBody = readResponse(connection.getInputStream());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                Toast.makeText(cancelbooking.this, "Booking cancelled successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(cancelbooking.this, "Failed to cancel booking: " + responseBody, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String readResponse(InputStream inputStream) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            responseBuilder.append(new String(buffer, 0, bytesRead));
        }
        inputStream.close();
        return responseBuilder.toString();
    }
}