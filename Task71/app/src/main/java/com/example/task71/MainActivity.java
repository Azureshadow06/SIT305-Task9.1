package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button listButton = findViewById(R.id.listButton);
        Button mapButton = findViewById(R.id.buttonMaps);

        db = new DatabaseHelper(this);//Active SQLite database


        signUpButton.setOnClickListener(new View.OnClickListener() {//Press this button to open Post New Advert Window
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {//Press this button to open Lost&Found list Window
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(listIntent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });


    }
}