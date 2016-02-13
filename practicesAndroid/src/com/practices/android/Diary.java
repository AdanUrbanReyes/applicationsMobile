package com.practices.android;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.LinkedList;
import android.view.View.OnClickListener;
import android.view.View;

public class Diary extends Activity implements OnClickListener{
    private Button register,show,remove;
    private TextView showLabel;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        register=(Button)findViewById(R.id.register);
        show=(Button)findViewById(R.id.show);
        remove=(Button)findViewById(R.id.remove);

        showLabel=(TextView)findViewById(R.id.showLabel);
        
        showLabel.setVisibility(View.INVISIBLE);
        register.setOnClickListener(this);
        show.setOnClickListener(this);
        remove.setOnClickListener(this);
    }
    public void showRegister(){
        int i;
        String aux="";
        HandlerSQLite sqlite=new HandlerSQLite(this);
        sqlite.openDataBase();
        LinkedList<Contacto> response=sqlite.readRegisters();
        sqlite.close();
        for(i=0;i<response.size();i++)
            aux+="name: "+response.get(i).getname()+"\n"+"phone: "+response.get(i).getphone()+"\n"+"email: "+response.get(i).getemail()+"\n";
        showLabel.setText(aux);
    }
    @Override
    public void onClick(View v){
        Button button;
        Intent intent;
        if(v instanceof Button){
            button=(Button)v;
            if(button==register){
                showLabel.setVisibility(View.INVISIBLE);
                intent=new Intent(this,NewContact.class);
                startActivity(intent);
            }else{
                if(button==show){
                    showLabel.setVisibility(View.VISIBLE);
                    showRegister();
                }else{
                    if(button==remove){
                        showLabel.setVisibility(View.INVISIBLE);
                        intent =new Intent(this,RemoveContact.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}