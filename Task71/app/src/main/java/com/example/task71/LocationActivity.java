package com.example.task71;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationTextView;
    Button toastButton;
    String location;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationTextView = findViewById(R.id.locationTextView);
        toastButton = findViewById(R.id.toastButton);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final String[] loc = new String[1];
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationTextView.setText(location.toString());
                loc[0] = location.toString();

            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }




        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = loc[0];
                if (location != null) {
                    String[] locationParts = location.split("[\\s,]+");
                    double latitude = Double.parseDouble(locationParts[1]);
                    double longitude = Double.parseDouble(locationParts[2]);
                    String formattedLocation = String.format("%f,%f", latitude, longitude);
                    Toast.makeText(LocationActivity.this, "Location: " + formattedLocation, Toast.LENGTH_SHORT).show();

                    Intent locateIntent = new Intent(LocationActivity.this, SignupActivity.class);
                    locateIntent.putExtra("formattedLocation", formattedLocation);
                    startActivity(locateIntent);
                } else {
                    Toast.makeText(LocationActivity.this, "Location not available, plz try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}