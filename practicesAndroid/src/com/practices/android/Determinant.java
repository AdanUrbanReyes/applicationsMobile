package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
public class Determinant extends Activity implements OnClickListener{
    private EditText matriz;
    private TextView response;
    private Button calcular;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.determinant);
        matriz=(EditText)findViewById(R.id.matriz);
        response=(TextView)findViewById(R.id.response);
        calcular=(Button)findViewById(R.id.calcular);
        calcular.setOnClickListener(this);
    }
    public int calculaDeterminante(int [][]matriz,int n){
        if(n==2)
            return matriz[0][0]*matriz[1][1]-matriz[0][1]*matriz[1][0];
        int c,i,j,k,cta=0;
        for(c=0;c<n;c++){
            int [][]submatriz=new int [n-1][n-1];
            for(i=1;i<n;i++){
                for(j=0,k=0;j<n;j++){
                    if(j!=c)
                        submatriz[i-1][k++]=matriz[i][j];
                }
            }
            if(c%2==0)
                cta+=matriz[0][c]*calculaDeterminante(submatriz,n-1);
            else
                cta-=matriz[0][c]*calculaDeterminante(submatriz,n-1);
        }
        return cta;
    }
    public void onClick(View v){
        int i,j;
        String []columns,rows=matriz.getText().toString().split("=");//c11,c12-c21,c22
        int [][]matriz=new int[rows.length][rows.length];
        for(i=0;i<rows.length;i++){
            columns=rows[i].split(",");
            for(j=0;j<rows.length;j++){
                matriz[i][j]=Integer.parseInt(columns[j]);
            }
        }
        int resultado=calculaDeterminante(matriz,rows.length);
        resultado=Math.abs(resultado);
        response.setText("determinante = "+resultado);
    }
}
