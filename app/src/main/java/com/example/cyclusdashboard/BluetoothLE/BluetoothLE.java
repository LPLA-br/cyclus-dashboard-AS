package com.example.cyclusdashboard.BluetoothLE;

//IMPORTS COMPONENTES ANDROID
import android.Manifest;

import androidx.core.app.ActivityCompat; //permissões
import androidx.core.content.PermissionChecker;

import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Handler; //threads

// IMPORTS BLUETOOTH
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;

import java.util.Vector;

public class BluetoothLE
{}

/* Desenvolvimento congelado devido a fusão entre
código fonte aberto de SimpleBle com Cyclus-dashboard
public class BluetoothLE
{
    protected BluetoothManager bluetoothManager;
    protected BluetoothAdapter bluetoothAdapter;
    protected BluetoothLeScanner bluetoothLeScanner;
    protected BluetoothDevice bluetoothDevice;

    protected final String targetEsp32ServiceUUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b";
    protected final String targetEsp32CharacteristicUUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8";

    boolean scanning;
    int SCAN_TIME = 10000;
    private final Vector<String> escaneados;

    public BluetoothService()
    {
        this.scanning = false;
        this.escaneados = new Vector<String>(255);
    }

    //SETOR DE CALLBACKS PARA: ScanCallback() usado por this.bluetoothLeScanner

    private ScanCallback leScanCallback  = new ScanCallback()
    {
        @Override
        public void onScanResult(int callbackType, ScanResult result)
        {
            super.onScanResult(callbackType, result);
            // getDevice() retorna BluetoothDevice
            // escaneados não alterável d'aqui pois aqui é a famigerada callback.
            this.escaneados.add(result.getDevice());
        }
    };

    //SETOR DE MÉTODOS DE FUNÇÕES

    private void scanLeDevice()
    {
        if (!scanning)
        {
            //thread será executada em 10 segundos
            handler.postDelayed( new Runnable()
            {
                @Override
                public void run()
                {
                    scanning = false;

                    if (checkSelfPermission( this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED)
                    {
                        bluetoothLeScanner.stopScan(new ScanCallback()
                        {
                            @Override
                            public void onScanResult(int callbackType, ScanResult result)
                            {
                                super.onScanResult(callbackType, result);
                            }
                        });
                    }
                    else stopSelf();
                }
            }, SCAN_PERIOD);
            scanning = true;
            bluetoothLeScanner.startScan(leScanCallback);
        }
        else
        {
            scanning = false;
            bluetoothLeScanner.stopScan(leScanCallback);
        }
    }

    //SETOR DE MÉTODOS SOBRESCRITOS DO SERVICE

    @Override
    public void onCreate()
    {
        this.bluetoothManager = getSystemService(BluetoothManager.class);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        this.bluetoothDevice = null;
        if (bluetoothAdapter == null)
        {
            // Device doesn't support Bluetooth
            stopSelf();
        }
        else
        {
            if (!bluetoothAdapter.isEnabled())
            {
                //bluetooth inativo. Requisitar ativação ao usuário
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //this.atividade.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                if(!bluetoothAdapter.isEnabled()) stopSelf();
            }
            else
            {
                //Procurar dispositivo para poder obter dispositivo via:
                //    this.bluetoothDevice().getRemoteDevice("MAC_BLE");
                //  Através de:
                //    this.bluetoothAdapter;
                this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
                //TODO: continuar caso ederdoski não funcione
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
*/