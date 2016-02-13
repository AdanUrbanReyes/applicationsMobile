package com.practices.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

public class BeautifulNumber extends Activity implements OnClickListener{
    private Button button;
    private EditText number;
    private TextView error;
    private String aux;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.beautifulnumber);
        error=(TextView)findViewById(R.id.error);
        number=(EditText)findViewById(R.id.number);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    public void numberBeautiful(int number){
        if(number<1){
            aux+="\nnot was number beautiful\n";
            return;
        }
        while(number!=1){
            aux+=" "+number+" ";
            if(number%2==0)
                number/=2;
            else
                number=number*3+1;
        }
        aux+="\twas a number beatiful\n";
    }
    public boolean isPrimo(int number){
        int i;
        for(i=2;i<number;i++)
            if(number%i==0)
                return false;
        return true;
    }
    public void numbersPrimos(int tope){
        int number;
        aux+="\nlos primeros "+tope+" numeros primos\n";
        for(number=1;tope>0;number++){
            if(isPrimo(number)){
                aux+=" "+number+" ";
                tope--;
            }
        }
    }
    public void fibonacci(){
        int x=0,y=1,z,cont;
        aux+="\nfibonacci\n0, 1, ";
        for (cont=1;cont<=7;cont=cont+1){
          z=x+y;
          aux+=""+z+", ";
          x=y;
          y=z;
        }  
    }
    public void onClick(View v){
        error.setText("");
        aux="";
        try{
            int number=Integer.parseInt(this.number.getText().toString());
            numberBeautiful(number);
            numbersPrimos(5);
            fibonacci();
            error.setText(aux);
        }catch(NumberFormatException e){
            error.setText("please enter a number integer!");
        }
    }

    
}
