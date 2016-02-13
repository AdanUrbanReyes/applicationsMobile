package com.bluetooth.android;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// Debugging
public static final String TAG = "LEDv0";
public static final boolean D = true;
// Tipos de mensaje enviados y recibidos desde el Handler de ConexionBT
public static final int Mensaje_Estado_Cambiado = 1;
public static final int Mensaje_Leido = 2;
public static final int Mensaje_Escrito = 3;
public static final int Mensaje_Nombre_Dispositivo = 4;
public static final int Mensaje_TOAST = 5;
public static final int MESSAGE_Desconectado = 6;
public static final int REQUEST_ENABLE_BT = 7;
public static final String DEVICE_NAME = "device_name";
public static final String TOAST = "toast";
//Nombre del dispositivo conectado
private String mConnectedDeviceName = null;
// Adaptador local Bluetooth
private BluetoothAdapter AdaptadorBT = null;
//Objeto miembro para el servicio de ConexionBT
private ConnectionBluetooth Servicio_BT = null;
//Vibrador
private Vibrator vibrador;
//variables para el Menu de conexión
private boolean seleccionador=false;
public int Opcion=R.menu.activity_main;
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
final ToggleButton BotonLed = (ToggleButton)findViewById(R.id.Led1);
BotonLed.setOnClickListener(new View.OnClickListener() {
public void onClick(View vv) {
if(BotonLed.isChecked()) {
if(D) Log.e("BotonLed", "Encendiendo..");
sendMessage("1\r");
findViewById(R.id.Led1).setBackgroundResource(R.drawable.ledcamaleon2);
}
else {
if(D) Log.e("BotonLed", "Apagando..");
sendMessage("2\r");
findViewById(R.id.Led1).setBackgroundResource(R.drawable.ledcamaleon1);
}
}
});//fin de metodo de BotonLed
}
public void onStart() {
super.onStart();
ConfigBT();
}
@Override
public void onDestroy(){
super.onDestroy();
if (Servicio_BT != null) Servicio_BT.stop();//Detenemos servicio
}
public void ConfigBT(){
// Obtenemos el adaptador de bluetooth
AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
if (AdaptadorBT.isEnabled()) {//Si el BT esta encendido,
if (Servicio_BT == null) {//y el Servicio_BT es nulo, invocamos el Servicio_BT
Servicio_BT = new ConexionBT(this, mHandler);
}
}
else{ if(D) Log.e("Setup", "Bluetooth apagado...");
Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
}
}
public void onActivityResult(int requestCode, int resultCode, Intent data) {
//Una vez que se ha realizado una actividad regresa un "resultado"...
switch (requestCode) {
case REQUEST_ENABLE_BT://Respuesta de intento de encendido de BT
if (resultCode == Activity.RESULT_OK) {//BT esta activado,iniciamos servicio
ConfigBT();
} else {//No se activo BT, salimos de la app
finish();}
}//fin de switch case
}//fin de onActivityResult
@Override
public boolean onPrepareOptionsMenu(Menu menux){
//cada vez que se presiona la tecla menu este metodo es llamado
menux.clear();//limpiamos menu actual
if (seleccionador==false)Opcion=R.menu.activity_main;//dependiendo las necesidades
if (seleccionador==true)Opcion=R.menu.desconecta; // crearemos un menu diferente
getMenuInflater().inflate(Opcion, menux);
return super.onPrepareOptionsMenu(menux);
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {
case R.id.Conexion:
if(D) Log.e("conexion", "conectandonos");
vibrador = (Vibrator) getSystemService(VIBRATOR_SERVICE);
vibrador.vibrate(1000);
String address = "00:06:66:42:A7:C5";//Direccion Mac del rn42
BluetoothDevice device = AdaptadorBT.getRemoteDevice(address);
Servicio_BT.connect(device);
return true;
case R.id.desconexion:
if (Servicio_BT != null) Servicio_BT.stop();//Detenemos servicio
return true;
}//fin de swtich de opciones
return false;
}//fin de metodo onOptionsItemSelected
public void sendMessage(String message) {
if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED) {//checa si estamos conectados a BT
if (message.length() > 0) { // checa si hay algo que enviar
byte[] send = message.getBytes();//Obtenemos bytes del mensaje
if(D) Log.e(TAG, "Mensaje enviado:"+ message);
Servicio_BT.write(send); //Mandamos a escribir el mensaje
}
} else Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
}//fin de sendMessage
final Handler mHandler = new Handler() {
@Override
public void handleMessage(Message msg) {
switch (msg.what) {
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
case Mensaje_Escrito:
byte[] writeBuf = (byte[]) msg.obj;//buffer de escritura...
// Construye un String del Buffer
String writeMessage = new String(writeBuf);
if(D) Log.e(TAG, "Message_write =w= "+ writeMessage);
break;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
case Mensaje_Leido:
byte[] readBuf = (byte[]) msg.obj;//buffer de lectura...
//Construye un String de los bytes validos en el buffer
String readMessage = new String(readBuf, 0, msg.arg1);
if(D) Log.e(TAG, "Message_read =w= "+ readMessage);
break;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
case Mensaje_Nombre_Dispositivo:
mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); //Guardamos nombre del dispositivo
Toast.makeText(getApplicationContext(), "Conectado con "+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
seleccionador=true;
break;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
case Mensaje_TOAST:
Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
Toast.LENGTH_SHORT).show();
break;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
case MESSAGE_Desconectado:	
if(D) Log.e("Conexion","DESConectados");
seleccionador=false;
break;
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}//FIN DE SWITCH CASE PRIMARIO DEL HANDLER
}//FIN DE METODO INTERNO handleMessage
};//Fin de Handler
}//Fin MainActivity



/*import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID; 
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnItemClickListener {
 
    ArrayAdapter<String> listAdapter;
    ListView listView;
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String> pairedDevices;
    ArrayList<BluetoothDevice> devices;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    protected static final int SUCCESS_CONNECT = 0;
    protected static final int MESSAGE_READ = 1;
    IntentFilter filter;
    BroadcastReceiver receiver;
    String tag = "debugging";
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.i(tag, "in handler");
            super.handleMessage(msg);
            switch(msg.what){
            case SUCCESS_CONNECT:
                // DO something
                ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                Toast.makeText(getApplicationContext(), "CONNECT", 0).show();
                String s = "successfully connected";
                connectedThread.write(s.getBytes());
                Log.i(tag, "connected");
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[])msg.obj;
                String string = new String(readBuf);
                Toast.makeText(getApplicationContext(), string, 0).show();
                break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        if(btAdapter==null){
            Toast.makeText(getApplicationContext(), "No bluetooth detected", 0).show();
            finish();
        }
        else{
            if(!btAdapter.isEnabled()){
                turnOnBT();
            }
             
            getPairedDevices();
            startDiscovery();
        }
 
 
    }
    private void staXrtDiscovery() {
        // TODO Auto-generated method stub
        btAdapter.cancelDiscovery();
        btAdapter.startDiscovery();
         
    }
    private void turnOnBT() {
        // TODO Auto-generated method stub
        Intent intent =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 1);
    }
    private void getPairedDevices() {
        // TODO Auto-generated method stub
        devicesArray = btAdapter.getBondedDevices();
        if(devicesArray.size()>0){
            for(BluetoothDevice device:devicesArray){
                pairedDevices.add(device.getName());
                 
            }
        }
    }
    private void init() {
        // TODO Auto-generated method stub
        listView=(ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
        listView.setAdapter(listAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<String>();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        devices = new ArrayList<BluetoothDevice>();
        receiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                 
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    devices.add(device);
                    String s = "";
                    for(int a = 0; a < pairedDevices.size(); a++){
                        if(device.getName().equals(pairedDevices.get(a))){
                            //append
                            s = "(Paired)";
                            break;
                        }
                    }
             
                    listAdapter.add(device.getName()+" "+s+" "+"\n"+device.getAddress());
                }
                 
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                    // run some code
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    // run some code
             
                     
                 
                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if(btAdapter.getState() == btAdapter.STATE_OFF){
                        turnOnBT();
                    }
                }
           
            }
        };
         
        registerReceiver(receiver, filter);
         filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver, filter);
         filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
         filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);
    }
     
     
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(receiver);
    }
 
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Bluetooth must be enabled to continue", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO Auto-generated method stub
             
            if(btAdapter.isDiscovering()){
                btAdapter.cancelDiscovery();
            }
            if(listAdapter.getItem(arg2).contains("Paired")){
         
                BluetoothDevice selectedDevice = devices.get(arg2);
                ConnectThread connect = new ConnectThread(selectedDevice);
                connect.start();
                Log.i(tag, "in click listener");
            }
            else{
                Toast.makeText(getApplicationContext(), "device is not paired", 0).show();
            }
        }
         
        private class ConnectThread extends Thread {
         
            private final BluetoothSocket mmSocket;
            private final BluetoothDevice mmDevice;
          
            public ConnectThread(BluetoothDevice device) {
                // Use a temporary object that is later assigned to mmSocket,
                // because mmSocket is final
                BluetoothSocket tmp = null;
                mmDevice = device;
                Log.i(tag, "construct");
                // Get a BluetoothSocket to connect with the given BluetoothDevice
                try {
                    // MY_UUID is the app's UUID string, also used by the server code
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (IOException e) {
                    Log.i(tag, "get socket failed");
                     
                }
                mmSocket = tmp;
            }
          
            public void run() {
                // Cancel discovery because it will slow down the connection
                btAdapter.cancelDiscovery();
                Log.i(tag, "connect - run");
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket.connect();
                    Log.i(tag, "connect - succeeded");
                } catch (IOException connectException) {    Log.i(tag, "connect failed");
                    // Unable to connect; close the socket and get out
                    try {
                        mmSocket.close();
                    } catch (IOException closeException) { }
                    return;
                }
          
                // Do work to manage the connection (in a separate thread)
            
                mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
            }
          
 
 
            /** Will cancel an in-progress connection, and close the socket */
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) { }
            }
        }
 
        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
          
            public ConnectedThread(BluetoothSocket socket) {
                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;
          
                // Get the input and output streams, using temp objects because
                // member streams are final
                try {
                    tmpIn = socket.getInputStream();
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) { }
          
                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }
          
            public void run() {
                byte[] buffer;  // buffer store for the stream
                int bytes; // bytes returned from read()
     
                // Keep listening to the InputStream until an exception occurs
                while (true) {
                    try {
                        // Read from the InputStream
                        buffer = new byte[1024];
                        bytes = mmInStream.read(buffer);
                        // Send the obtained bytes to the UI activity
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget();
                        
                    } catch (IOException e) {
                        break;
                    }
                }
            }
          
            /* Call this from the main activity to send data to the remote device */
            public void write(byte[] bytes) {
                try {
                    mmOutStream.write(bytes);
                } catch (IOException e) { }
            }
          
            /* Call this from the main activity to shutdown the connection */
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) { }
            }
        }
}
