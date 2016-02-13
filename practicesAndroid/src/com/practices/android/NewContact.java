package com.practices.android;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class NewContact extends Activity implements View.OnClickListener {
    private Button executeRegister;
    private EditText name,phone,email;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.newcontacto);
        name=(EditText)findViewById(R.id.nameField);
        phone=(EditText)findViewById(R.id.phoneField);
        email=(EditText)findViewById(R.id.emailField);
        executeRegister=(Button)findViewById(R.id.executeRegister);
        executeRegister.setOnClickListener(this);
    }
    public void registrar(){
        String name=this.name.getText().toString();
        String email=this.email.getText().toString();
        String phone=this.phone.getText().toString();
        Contacto c=new Contacto(name, phone, email);
        HandlerSQLite sqlite=new HandlerSQLite(this);
        sqlite.openDataBase();
        sqlite.insertNewRegister(c);
        sqlite.closeDataBase();
        AlertDialog alert=new AlertDialog.Builder(this).create();
        alert.setTitle("Ok");
        alert.setMessage("contact added succefull");
        alert.show();
    }
    @Override
    public void onClick(View v) {
        Button b;
        if(v instanceof Button){
            b=(Button)v;
            if(b==executeRegister){
                registrar();
            }
        }
    }

}