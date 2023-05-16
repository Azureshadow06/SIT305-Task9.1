package com.example.task71;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.task71.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper db;
    private ArrayList<User> userList;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        userList = new ArrayList<>();
        db = new DatabaseHelper(this);
        userList = db.readItem();
        double val1 = 0;
        double val2 = 0;
        LatLng[] locations = new LatLng[userList.size()];
        String[] itemNames = new String[userList.size()];

        for (int j = 0; j <userList.size(); j++)
        {
            User user = userList.get(j);
            String a = user.getLocation();
            String[] parts = a.split(",");
            val1 = Double.parseDouble(parts[0]);
            val2 = Double.parseDouble(parts[1]);
            LatLng markdown = new LatLng(val1, val2);
            locations[j] = markdown;
            itemNames[j] = user.getUsername();
        }



        for (int i = 0; i < locations.length; i++) {
            mMap.addMarker(new MarkerOptions().position(locations[i]).title("ID: " + (i+1) + ", Item Name: " + itemNames[i]));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locations[0]));

    }


}