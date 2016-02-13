package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
public class MatrizesAddition extends Activity implements OnClickListener{
    private EditText matrizOne,matrizTwo;
    private TextView response;
    private Button makePlus;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrizesaddition);
        matrizOne=(EditText)findViewById(R.id.matrizOne);
        matrizTwo=(EditText)findViewById(R.id.matrizTwo);
        response=(TextView)findViewById(R.id.response);
        makePlus=(Button)findViewById(R.id.makePlus);
        makePlus.setOnClickListener(this);
    }
    public int [][] getMatriz(String []rows){
        int i,j;
        int [][]matriz=new int[rows.length][rows.length];
        String []columns;
        for(i=0;i<rows.length;i++){
            columns=rows[i].split(",");
            for(j=0;j<rows.length;j++){
                matriz[i][j]=Integer.parseInt(columns[j]);
            }
        }
        return matriz;
    }
    public int[][] plusMatriz(int [][]matrizOne,int[][]matrizTwo,int size){
        int i,j;
        int [][]resultado=new int[size][size];
        for(i=0;i<size;i++){
            for(j=0;j<size;j++){
                resultado[i][j]=matrizOne[i][j]+matrizTwo[i][j];
            }
        }
        return resultado;
    }
    public void onClick(View v){
        int i,j;
        String []rows;
        rows=matrizOne.getText().toString().split("=");//c11,c12-c21,c22
        int [][]matrizOne=getMatriz(rows);
        rows=matrizTwo.getText().toString().split("=");
        int [][]matrizTwo=getMatriz(rows);
        int [][]resultado=plusMatriz(matrizOne, matrizTwo,rows.length);
        String aux="";
        for(i=0;i<rows.length;i++){
            for(j=0;j<rows.length;j++)
                aux+=resultado[i][j]+"   ";
            aux+="\n";
        }
        response.setText(aux);
    }
}
