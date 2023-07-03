package com.example.pat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class trekrecord extends AppCompatActivity {

    private ListView trekrecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trekrecord);

        trekrecordList= findViewById(R.id.trekrecordList);

        new datatrekrecord().execute();

    }
    private class datatrekrecord extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                URL url = new URL("http://192.168.146.73:7000/trekrecord");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    result = response.toString();
                } else {
                    result = "Error: " + responseCode;
                }
                connection.disconnect();
            } catch (Exception e) {
                result = "Error: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> trekrecordlist = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject record = jsonArray.getJSONObject(i);

                    String idbooking = record.getString("idbooking");
                    String tanggal = record.getString("tanggal");
                    String waktu = record.getString("waktu");
                    String history_kunjungan = record.getString("history_kunjungan");
                    String jenis_layanan = record.getString("jenis_layanan");

                    String detailrecord = "History Kunjungan: " + trekrecordList + "\n"
                            + "idbooking: " + idbooking + "\n"
                            + "Tanggal: " + tanggal + "\n"
                            + "Waktu: " + waktu + "\n"
                            + "History Kunjungan: " + history_kunjungan + "\n"
                            + "Jenis Layanan: " + jenis_layanan;

                    trekrecordlist.add(detailrecord);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(trekrecord.this,
                        android.R.layout.simple_list_item_1, trekrecordlist);
                trekrecordList.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(trekrecord.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}