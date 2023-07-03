package com.example.pat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class booking extends AppCompatActivity {

    private EditText tanggalField;
    private EditText waktuField;
    private EditText usernameField;
    private Spinner jenis_mobilField;
    private Spinner jenis_layananField;
    private Spinner lokasiField;
    private Button bookButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        tanggalField = findViewById(R.id.tanggalField);
        waktuField = findViewById(R.id.waktuField);
        usernameField = findViewById(R.id.usernameField);
        jenis_mobilField = findViewById(R.id.jenis_mobilField);
        jenis_layananField = findViewById(R.id.jenis_layananField);
        lokasiField = findViewById(R.id.lokasiField);
        bookButton = findViewById(R.id.bookButton);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
}