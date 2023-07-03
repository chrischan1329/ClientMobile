package com.example.pat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mainmenu extends AppCompatActivity  {
    private Button bookingButton;
    private Button cancel_bookingButton;
    private Button trekrecordButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        bookingButton = findViewById(R.id.bookingField);
        cancel_bookingButton = findViewById(R.id.cancel_bookingField);
        trekrecordButton = findViewById(R.id.trekrecordField);
        logoutButton = findViewById(R.id.logoutField);

        trekrecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this, trekrecord.class);
                startActivity(intent);
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this, booking.class);
                startActivity(intent);
            }
        });
        cancel_bookingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this, cancelbooking.class);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this, login.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.jenis_mobilField:
//                // Handle Jenis Mobil button click
//                openjenismobil();
//                break;
////            case R.id.jenis_layananField:
////                 //Handle Jenis Layanan button click
////                openjenislayanan();
////                break;
//            case R.id.bookingField:
//                // Handle Booking button click
//                openbooking();
//                break;
//            case R.id.cancel_bookingField:
//                // Handle Cancel Booking button click
//                opencancelBooking();
//                break;
//        }
//    }
//
//    private void openjenismobil() {
//        Intent intent = new Intent(this, jenismobil.class);
//        startActivity(intent);
//    }
//
//    private void openbooking() {
//        Intent intent = new Intent(this, booking.class);
//        startActivity(intent);
//    }
//
//    private void openListKendaraanActivity() {
//        Intent intent = new Intent(this, jenismobil.class);
//        startActivity(intent);
//    }
//
//    private void opencancelBooking() {
//        Intent intent = new Intent(this, cancelbooking.class);
//        startActivity(intent);
//    }
}