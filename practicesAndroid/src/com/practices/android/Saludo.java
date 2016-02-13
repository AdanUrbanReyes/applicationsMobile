package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
public class Saludo extends Activity implements OnClickListener{
    private TextView saludo=null;
    private Button button=null;
    private String name=null;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saludo);
        saludo=(TextView)findViewById(R.id.saludo);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    public void onClick(View v){
        name=((EditText)findViewById(R.id.enter)).getText().toString();
        saludo.setText("hi "+name+" this is my first program in android");
    }
}
