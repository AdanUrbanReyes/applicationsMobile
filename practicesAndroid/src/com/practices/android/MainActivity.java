package com.practices.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MainActivity extends Activity implements OnClickListener{
    private Button beautifulNumber,moveTextBetweenFiels,divicion,determinante,matrizesAddition,saludo,palindroma,image,diary;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        beautifulNumber=(Button)findViewById(R.id.numberBeautiful);
        beautifulNumber.setOnClickListener(this);
        moveTextBetweenFiels=(Button)findViewById(R.id.moveTextBetweenFields);
        moveTextBetweenFiels.setOnClickListener(this);
        divicion=(Button)findViewById(R.id.divicion);
        divicion.setOnClickListener(this);
        determinante=(Button)findViewById(R.id.determinant);
        determinante.setOnClickListener(this);        
        matrizesAddition=(Button)findViewById(R.id.matrizesAddition);
        matrizesAddition.setOnClickListener(this);
        saludo=(Button)findViewById(R.id.saludo);
        saludo.setOnClickListener(this);
        palindroma=(Button)findViewById(R.id.palindroma);
        palindroma.setOnClickListener(this);
        image=(Button)findViewById(R.id.imagen);
        image.setOnClickListener(this);
        diary=(Button)findViewById(R.id.diary);
        diary.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Button button=(Button)v;
        Intent intent;
        if(button==beautifulNumber){
            intent=new Intent(this,BeautifulNumber.class);
            startActivity(intent);
        }else{
            if(button==moveTextBetweenFiels){
                intent=new Intent(this,MoveTextBetweenFields.class);
                startActivity(intent);
            }else{
                if(button==divicion){
                    intent=new Intent(this,Divicion.class);
                    startActivity(intent);
                }else{
                    if(button==determinante){
                        intent=new Intent(this,Determinant.class);
                        startActivity(intent);
                    }else{
                        if(button==matrizesAddition){
                            intent=new Intent(this,MatrizesAddition.class);
                            startActivity(intent);
                        }else{
                            if(button==saludo){
                                intent=new Intent(this,Saludo.class);
                                startActivity(intent);
                            }else{
                                if(button==palindroma){
                                    intent=new Intent(this,Palindroma.class);
                                    startActivity(intent);
                                }else{
                                    if(button==image){
                                        intent=new Intent(this,Image.class);
                                        startActivity(intent);
                                    }else{
                                        if(button==diary){
                                            intent=new Intent(this,Diary.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
