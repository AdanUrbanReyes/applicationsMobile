package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.LinkedList;

public class Palindroma extends Activity implements OnClickListener{
    public class HowCharacteres{
        private char character;
        private int how=1;
        public HowCharacteres(char character){
            this.character=character;
        }
        public void incrementHow(){
            how++;
        }
    }
    private TextView response;
    //private EditText enter;
    private String aux="";
    private View button;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palindroma);
        response=(TextView)findViewById(R.id.responseID);
        button=findViewById(R.id.buttonID);
        button.setOnClickListener(this);

    }
    public void onClick(View v) {
        EditText enter=(EditText)findViewById(R.id.enterID);
        String cadena=enter.getText().toString();
        aux=""+cadena.length();
        if(isPalindroma(cadena))
            aux+="\nsi es palindroma";
        else
            aux+="\nno es palindroma";
        aux+="\n"+cadenaReverse(cadena);
        countCharacteres(cadena);
        response.setText(aux);
    }
    public int containsCharacter(LinkedList<HowCharacteres> list,char character){
        int i;
        for(i=0;i<list.size();i++){
            if(list.get(i).character==character)
                return i;
        }
        return -1;
    }
    public void putCount(LinkedList <HowCharacteres> list){
        int i;
        aux+="\ncharacters counted";
        for(i=0;i<list.size();i++)
            aux+="\n"+list.get(i).how+" "+list.get(i).character;
    }
    public void countCharacteres(String cadena){
        LinkedList <HowCharacteres> list=new LinkedList<HowCharacteres>();
        int i,j;
        for(i=0;i<cadena.length();i++){
            if((j=containsCharacter(list,cadena.charAt(i)))>-1)
                list.get(j).incrementHow();
            else
                list.add(new HowCharacteres(cadena.charAt(i)));
        }
        putCount(list);
    }
    public String cadenaReverse(String cadena){
        String cadenaReverse="";
        int i;
        for(i=cadena.length()-1;i>=0;i--)
            cadenaReverse+=cadena.charAt(i);
        return cadenaReverse;
    }
    public boolean isPalindroma(String cadena){
        if(cadena.equals(cadenaReverse(cadena)))
            return true;
        return false;
    }

}
