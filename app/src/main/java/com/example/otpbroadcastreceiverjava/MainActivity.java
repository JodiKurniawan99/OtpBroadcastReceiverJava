package com.example.otpbroadcastreceiverjava;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPermission = findViewById(R.id.btn_permission);
        btnPermission.setOnClickListener(this);
    }
    public ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission) {
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS);
        }
    }

}