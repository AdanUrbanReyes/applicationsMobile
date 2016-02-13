package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
public class Divicion extends Activity implements OnClickListener{
    private EditText numerador,denominador;
    private Button makeDivicion;
    private TextView response;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.divicion);
        numerador=(EditText)findViewById(R.id.numerador);
        denominador=(EditText)findViewById(R.id.denominador);
        response=(TextView)findViewById(R.id.response);
        makeDivicion=(Button)findViewById(R.id.button);
        makeDivicion.setOnClickListener(this);
    }
    public String makeDivicion(String numerador,String denominador){
        int i;
        int flat=0;
        String residuo="",result="";
        double d=Double.parseDouble(denominador);
        double n;
        for(i=0;i<numerador.length();i++){
            if(flat>=1)result+="0";
            residuo+=numerador.charAt(i);
            n=Double.parseDouble(residuo);
            flat++;
            if(n>=d){//si se puede aser la divicion
                residuo=""+(int)(n%d);
                result+=(int)(n/d);
                flat=0;
            }
        }
        return ("\nresultado = "+result+"     resuido "+residuo);
    }
    public void onClick(View v){
        response.setText(makeDivicion(numerador.getText().toString(),denominador.getText().toString()));
    }
}
