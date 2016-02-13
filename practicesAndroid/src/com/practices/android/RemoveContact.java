package com.practices.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.View;

public class RemoveContact extends Activity implements OnClickListener{
    private EditText phone;
    private Button remove;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.removecontact);
        phone=(EditText)findViewById(R.id.phoneRemove);
        remove=(Button)findViewById(R.id.removeExecute);
        remove.setOnClickListener(this);
    }
    public void removeRegister(){
        String phone=this.phone.getText().toString();
        HandlerSQLite sqlite=new HandlerSQLite(this);
        sqlite.openDataBase();
        sqlite.removeRegister(phone);
        sqlite.close();
        AlertDialog alert=new AlertDialog.Builder(this).create();
        alert.setTitle("OK");
        alert.setMessage("the contact with phone "+phone+" was remove succefull");
        alert.show();
    }
    @Override
    public void onClick(View v){
        Button button=(Button)v;
        if(button==remove){
            removeRegister();
        }
    }
}
