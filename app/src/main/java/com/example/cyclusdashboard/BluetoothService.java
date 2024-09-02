package com.example.cyclusdashboard;

import android.app.Service;
import android.app.Activity;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.os.IBinder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;

public class BluetoothService extends Service
{
    protected BluetoothManager bluetoothManager;
    protected BluetoothAdapter bluetoothAdapter;
    protected Activity atividade;
    protected BluetoothLeScanner bluetoothLeScanner;

    public BluetoothService( Activity atividade )
    {
        this.bluetoothManager = getSystemService(BluetoothManager.class);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        this.atividade = atividade;
        if (bluetoothAdapter == null)
        {
            // Device doesn't support Bluetooth
        }
        else
        {
            if (!bluetoothAdapter.isEnabled())
            {
                //bluetooth inativo. Requisitar ativação ao usuário
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //this.atividade.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                //obtem-se escaneador bluetooth low energy. Metodos agora
                this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
            }
        }
    }

    public void buscarDispositivoBluetoothLE()
    {
        this.bluetoothLeScanner;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}