package com.practices.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.ImageView;
public class Image extends Activity implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton toggleButton=null;
    private ImageView imageView=null;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        imageView=(ImageView)findViewById(R.id.imageView);
        toggleButton.setOnCheckedChangeListener(this);
    }
    public void onCheckedChanged(CompoundButton cb,boolean flat){
        if(toggleButton.isChecked())
            imageView.setImageResource(R.drawable.itachi);
        else
            imageView.setImageResource(R.drawable.deidara);
    }
}
