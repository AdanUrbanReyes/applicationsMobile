package com.gps.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Set;

public class MainActivity extends Activity{
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> adapter;
    private Button connect;
    private ListView listDevice;
    Set<BluetoothDevice> devices;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        connect=(Button)findViewById(R.id.selectDevice);
        listDevice=(ListView)findViewById(R.id.listDevice);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,0);
        listDevice.setAdapter(adapter);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"no bluetooth detected",0).show();
            /*AlertDialog alert=new AlertDialog.Builder(this).create();
            alert.setTitle("error");
            alert.setMessage("fist activate bluetooth");
            alert.show();*/
        }else{
            if(!bluetoothAdapter.isEnabled()){
                Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
            getPairedDevices();
        }
        
    }
    public void getPairedDevices(){
        devices=bluetoothAdapter.getBondedDevices();
        if(devices.size()>0){
            for(BluetoothDevice device:devices){
                adapter.add(device.getName()+"\n"+device.getAddress()+"");
                
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //To change body of generated methods, choose Tools | Templates.
        if(resultCode==RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),"bluetooth must be enable to continue",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
}