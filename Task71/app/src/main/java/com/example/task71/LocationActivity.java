package com.example.task71;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationTextView;
    Button toastButton;
    String location;
    EditText editText;
    TextView textView1, textView2;
    Button displayLocationBtn;
    String locationAuto;



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


        //Use current location feature code
        toastButton = findViewById(R.id.toastButton);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final String[] loc = new String[1];
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
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
        else {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);}
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



        //Google AutomateComplete location feature code
        editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        displayLocationBtn = findViewById(R.id.button);
        Places.initialize(getApplicationContext(), "AIzaSyCRzCgO1cMYZaFI6GjKLIa0wQx0IBl9z4Y");
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(LocationActivity.this);

                startActivityForResult(intent, 100);
            }
        });
        displayLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = locationAuto;
                Toast.makeText(LocationActivity.this, location, Toast.LENGTH_SHORT).show();

                Intent locateIntent = new Intent(LocationActivity.this, SignupActivity.class);
                locateIntent.putExtra("formattedLocation", location);
                startActivity(locateIntent);
            }
        });



    }
    //Protected method for AutomateCompleted feature
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                editText.setText(place.getAddress());

                textView1.setText(String.format("Locality Name: %s", place.getName()));

                textView2.setText(String.valueOf(place.getLatLng()));

                String[] locationParts = String.valueOf(place.getLatLng()).split("[\\s(),]+");

                double latitude = Double.parseDouble(locationParts[1]);
                double longitude = Double.parseDouble(locationParts[2]);
                String formattedLocation = latitude + "," + longitude;
                locationAuto = formattedLocation;

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);

                Toast.makeText(LocationActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}