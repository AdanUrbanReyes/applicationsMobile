package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MoveTextBetweenFields extends Activity implements OnClickListener{
    private Button move;
    private EditText oneField,twoField;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.movetextbetweenfields);
        oneField=(EditText)findViewById(R.id.oneField);
        twoField=(EditText)findViewById(R.id.twoField);
        move=(Button)findViewById(R.id.move);
        move.setOnClickListener(this);
    }
    public void move(){
        String oneString=oneField.getText().toString();
        oneField.setText("");
        twoField.setText(oneString);
    }
    @Override
    public void onClick(View v) {
        Button button=(Button)v;
        if(button==move){
            move();
        }
    }
    
}
