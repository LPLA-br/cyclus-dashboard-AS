package com.example.cyclusdashboard;

import android.annotation.SuppressLint;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;

//SUB-COMPONENTES DA ACTIVITY PRINCIPAL (ELEMENTOS GRÁFICOS)
import android.view.View;

import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.util.Log;

// IMPORTS CLASSES CYCLUS
import com.example.cyclusdashboard.uteis.ConversorCyclus;
import com.example.cyclusdashboard.uteis.ExtratorCyclus;
import com.example.cyclusdashboard.uteis.TacometroCyclus;

//ELDERDOSKI ADAPTERS
import com.example.cyclusdashboard.ederdooski.adapters.BasicList;

//TESTE EDERDOSKI SIMPLEBLE
import com.example.cyclusdashboard.ederdoski.simpleble.interfaces.BleCallback;
import com.example.cyclusdashboard.ederdoski.simpleble.models.BluetoothLE;
import com.example.cyclusdashboard.ederdoski.simpleble.utils.BluetoothLEHelper;
import com.example.cyclusdashboard.ederdoski.simpleble.utils.Constants;

public class MainActivity extends AppCompatActivity
{

    private BluetoothLEHelper ble;
    private AlertDialog dAlert;

    private TacometroCyclus tacometro;
    private ConversorCyclus conversor;
    private ExtratorCyclus extrator;

    private Thread tarefaLeitura;
    private boolean leitura;

    private ListView listBle;
    private Button btnScan;
    private Button btnRead;
    private Button btnExit;
    private TextView dados;
    private TextView odometria;

    private int distancia;

    // INICIO SETOR DE CÓDIGO ADAPTADO

    /** Faz um alert surgir com título e texto
     *  @param title: String
     *  @param message: String
     *  @param btnVisible: String
     * */
    private AlertDialog setDialogInfo(String title, String message, boolean btnVisible){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_standard, null);

        TextView btnNeutral = view.findViewById(R.id.btnNeutral);
        TextView txtTitle   = view.findViewById(R.id.txtTitle);
        TextView txtMessage = view.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);

        if(btnVisible){
            btnNeutral.setVisibility(View.VISIBLE);
        }else{
            btnNeutral.setVisibility(View.GONE);
        }

        btnNeutral.setOnClickListener(view1 -> {
            dAlert.dismiss();
        });

        builder.setView(view);
        return builder.create();
    }

    /** Usa ble para construir lista de dispositivos conectáveis (ADVERTISING)
     *  Emprega Componente: BasicList para construção e retorno de lista.
     * */
    private void setList(){

        ArrayList<BluetoothLE> aBleAvailable  = new ArrayList<>();

        if(ble.getListDevices().size() > 0){
            for (int i=0; i<ble.getListDevices().size(); i++) {
                aBleAvailable.add(new BluetoothLE(ble.getListDevices().get(i).getName(), ble.getListDevices().get(i).getMacAddress(), ble.getListDevices().get(i).getRssi(), ble.getListDevices().get(i).getDevice()));
            }

            BasicList mAdapter = new BasicList(this, R.layout.simple_row_list, aBleAvailable) {
                @Override
                public void onItem(Object item, View view, int position) {

                    TextView txtName = view.findViewById(R.id.txtText);

                    String aux = ((BluetoothLE) item).getName() + "    " + ((BluetoothLE) item).getMacAddress();
                    txtName.setText(aux);

                }
            };

            listBle.setAdapter(mAdapter);
            listBle.setOnItemClickListener((parent, view, position, id) -> {
                BluetoothLE  itemValue = (BluetoothLE) listBle.getItemAtPosition(position);
                ble.connect(itemValue.getDevice(), bleCallbacks());
            });
        }else{
            dAlert = setDialogInfo("Ups", "We do not find active devices", true);
            dAlert.show();
        }
    }

    /** Sobrescreve métodos assícronos Callbacks do BluetoothGattCallback */
    private BleCallback bleCallbacks(){

        return new BleCallback(){

            @Override
            public void onBleConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onBleConnectionStateChange(gatt, status, newState);

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Connected to GATT server.", Toast.LENGTH_SHORT).show());
                }

                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Disconnected from GATT server.", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onBleServiceDiscovered(BluetoothGatt gatt, int status) {
                super.onBleServiceDiscovered(gatt, status);
                if (status != BluetoothGatt.GATT_SUCCESS) {
                    Log.e("Ble ServiceDiscovered","onServicesDiscovered received: " + status);
                }
            }

            @Override
            public void onBleCharacteristicChange(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onBleCharacteristicChange(gatt, characteristic);
                Log.i("BluetoothLEHelper","onCharacteristicChanged Value: " + Arrays.toString(characteristic.getValue()));
            }

            @Override
            public void onBleRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onBleRead(gatt, characteristic, status);

                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.i("TAG", Arrays.toString(characteristic.getValue()));
                    //runOnUiThread(() -> Toast.makeText(MainActivity.this, (new ConversorCyclus(characteristic.getValue()).converterByteArrayParaString()), Toast.LENGTH_SHORT).show());
                    runOnUiThread(() -> {
                        String dados;
                        int intervalo;
                        conversor.setDados(characteristic.getValue());
                        dados = conversor.converterByteArrayParaString();
                        try {
                            extrator.setStringJson(dados);
                            intervalo = Integer.parseInt( extrator.extrairDadosDeStringJson(0) );
                            tacometro.obterRpm(intervalo);
                            tacometro.obterVelocidadeKmh();
                            MainActivity.this.dados.setText( Integer.toString(tacometro.getVelocidadeKmh()) );
                        } catch ( Exception e ) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        /*try {
                            distancia += tacometro.getPERIMETRO_CIRCULAR_PNEU();
                            MainActivity.this.odometria.setText(Integer.toString(distancia));
                        } catch ( Exception e ) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                        }*/
                    });
                } else {
                    //feedback de fracasso
                    runOnUiThread( () -> Toast.makeText( MainActivity.this, "onBleRead() fracassou", Toast.LENGTH_SHORT).show() );
                }
            }

            @Override
            public void onBleWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onBleWrite(gatt, characteristic, status);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "onCharacteristicWrite Status : " + status, Toast.LENGTH_SHORT).show());
            }
        };
    }

    /** Emite alerta de acordo com o estado de escaneamento
     *  de dispositivos de ble.isScannig();
     *  Usa Handler para aguardar leitura e segue com
     *  uso do método this.setList();
     *  */
    private void scanCollars(){

        if(!ble.isScanning()) {

            dAlert = setDialogInfo("Scan in progress", "Loading...", false);
            dAlert.show();

            Handler mHandler = new Handler();
            ble.scanLeDevice(true);

            mHandler.postDelayed(() -> {
                dAlert.dismiss();
                setList();
            },ble.getScanPeriod());

        }
    }

    /** Define disparo de função ao clickar dos botões */
    private void listenerButtons(){

        btnScan.setOnClickListener(v -> {
            if(ble.isReadyForScan()){
                scanCollars();
            }else{
                Toast.makeText(MainActivity.this, "You must accept the bluetooth and Gps permissions or must turn on the bluetooth and Gps", Toast.LENGTH_SHORT).show();
            }
        });

        btnRead.setOnClickListener(v -> {
            leitura = !leitura;
            if ( leitura )
            {
                btnRead.setBackgroundColor( getResources().getColor(R.color.orange) );
            }
            else
            {
                btnRead.setBackgroundColor( getResources().getColor(R.color.red) );
            }
        });

        btnExit.setOnClickListener(v -> {
            ble.disconnect();
            finish();
        });

    }

    // FIM SETOR DE CÓDIGO ADAPTADO

    public void executarLeituraCaracteristica()
    {
        if( ble.isConnected() && leitura ) {
            try
            {
                ble.read(Constants.SERVICE_COLLAR_INFO, Constants.CHARACTERISTIC_CURRENT_POSITION);
            }
            catch( Exception error )
            {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Leitura de caracteristica fracassou !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void solicitarDefinicaoDePerimetroRoda(){

        AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
        mensagem.setTitle("CONFIGURACAO");
        mensagem.setMessage("Defina o perimetro externo da roda da frente em metros");

        final EditText input = new EditText(MainActivity.this);
        mensagem.setView(input);
        mensagem.setNeutralButton("DEFINIR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int novoValor = Integer.parseInt( input.getText().toString() );
                    tacometro.setPERIMETRO_CIRCULAR_PNEU( novoValor );
                }
                catch ( Exception e ) {
                    Toast.makeText(MainActivity.this, "Configuração de perímetro falhou", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mensagem.show();
    }

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

        //--- Initialize BLE Helper
        ble = new BluetoothLEHelper(this);

        tacometro = new TacometroCyclus();
        extrator = new ExtratorCyclus("{}");
        byte[] nada = {0x7b,0x7d};
        conversor = new ConversorCyclus( nada );
        leitura = false;

        solicitarDefinicaoDePerimetroRoda();

        listBle  = (ListView) findViewById(R.id.lista);
        btnScan  = (Button) findViewById(R.id.escanear);
        btnRead  = (Button) findViewById(R.id.ler);
        btnExit  = (Button) findViewById(R.id.sair);
        dados =    (TextView) findViewById(R.id.dados);
        odometria =(TextView) findViewById(R.id.odometro);

        listenerButtons();

        //Reorientação de tela gerará falha catastrofica na aplicação !
        this.tarefaLeitura = new Thread(()->
        {
            while( true ) {
                try {
                    Thread.sleep(100);
                    executarLeituraCaracteristica();
                } catch (InterruptedException e) {
                    Toast.makeText( MainActivity.this, e.toString(), Toast.LENGTH_SHORT ).show();
                    break;
                }
            }
        });
        this.tarefaLeitura.start();
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
        ble.disconnect();
        this.tarefaLeitura.interrupt();
    }
}