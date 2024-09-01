package com.example.cyclusdashboard;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.app.Activity;

public class CyclusBluetoothLE
{
    private String servicoUUID;
    private String caracteristicaUUID;
    private final Activity atividade;

    CyclusBluetoothLE( String serviceUUID, String characteristicUUID, Activity atividade )
    {
        this.servicoUUID = serviceUUID;
        this.caracteristicaUUID = characteristicUUID;
        this.atividade = atividade;
    }

    /** retorna o adaptador ou null*/
    private BluetoothAdapter verificarSeSmartphoneSuportaBluetooth()
    {
        BluetoothManager gerenciador = getSystemService( BluetoothManager.class );
        BluetoothAdapter adaptador = gerenciador.getAdapter();
        if ( adaptador == null )
        {
            return null;
        }
        else adaptador;
    }

    public void ativarBluetoothSeTiver()
    {
        BluetoothAdapter adaptador = this.verificarSeSmartphoneSuportaBluetooth();
        if ( adaptador == null ) return;
        else
        {
            if ( !adaptador.isEnabled() )
            {
                Intent ativarBluetooth = new Intent( a.ACTION_REQUEST_ENABLE );
                this.atividade.startActivityForResult(ativarBluetooth, REQUEST_ENABLE_BT );
            }
        }
    }

    public void buscarDispositvoPeloNomeAdvertising( String nomeAdvertising )
    {
        return;
    }

}
