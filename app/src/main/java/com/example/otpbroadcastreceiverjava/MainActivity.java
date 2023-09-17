package com.example.otpbroadcastreceiverjava;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    private BroadcastReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPermission = findViewById(R.id.btn_permission);
        Button btnDownload = findViewById(R.id.btn_download);
        btnPermission.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show();
            }
        };


        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        //this code for register receiver
        registerReceiver(downloadReceiver, downloadIntentFilter);
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
        }else if (v.getId() == R.id.btn_download) {
            final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                Intent notifyFinishIntent = new Intent().setAction(MainActivity.ACTION_DOWNLOAD_STATUS);
                sendBroadcast(notifyFinishIntent);
            }, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReceiver != null) {
            //this code used to unregister receiver object
            unregisterReceiver(downloadReceiver);
        }
    }

}