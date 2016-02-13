package com.example.GPS;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button btnActualizar;
    private Button btnDesactivar;
    private TextView lblLatitud;
    private TextView lblLongitud;
    private TextView lblPrecision;
    private TextView lblEstado;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private LocationProvider proveedor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnActualizar = (Button)findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button)findViewById(R.id.BtnDesactivar);
        lblLatitud = (TextView)findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView)findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView)findViewById(R.id.LblPosPrecision);
        lblEstado = (TextView)findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPosicion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(locationListener);
            }
        });
    }

    private void actualizarPosicion()
    {

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        muestraPosicion(location);


        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                muestraPosicion(location);
            }
            public void onProviderDisabled(String provider){
                lblEstado.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                lblEstado.setText("Provider ON");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("LocAndroid", "Provider Status: " + status);
                lblEstado.setText("Provider Status: " + status);
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void muestraPosicion(Location loc) {
        if(loc != null)
        {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("LocAndroid", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPrecision.setText("Precision: (sin_datos)");
        }
    }

    public void onProviderDisabled(String proveedor){}
    public void onProviderEnabled(String proveedor){}
    public void onStateChanged(String proveedor, int estado, Bundle b){}
}
