package com.example.cyclusdashboard;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); //*
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //BluetoothGatt cliente = new BluetoothGatt();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume()
    {
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}