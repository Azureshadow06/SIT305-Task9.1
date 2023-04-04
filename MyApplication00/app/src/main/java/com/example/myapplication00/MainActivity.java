package com.example.myapplication00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.widget.AdapterView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "Helo";
    String selectedItemFrom;
    String selectedItemTo;

    double insertNumber;
    double convertedNumber;

    Button button;
    TextView myTextView;
    EditText myEditText;
    Spinner mySpinner1;
    Spinner mySpinner2;
    EditText usernameEditText;

    Boolean loggedIn;

    public void jumpClick(View view){

        if (loggedIn){
            Toast.makeText(this, "You are now logged in", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("username", usernameEditText.getText().toString());
            startActivity(intent);
        }


    }

    public  void logOut(View view){
        if (loggedIn){
            loggedIn = false;
            Toast.makeText(this, "Account Signed Out", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "You didn't logged in yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView loggedTextView = findViewById((R.id.loggedTextView));
        Intent intent = getIntent();
        Boolean isLogged= intent.getBooleanExtra("login", false);
        String name = intent.getStringExtra("username");
        if(name == null)
        {
            loggedTextView.setText("Welcome, use JUMP to log in");
        }
        else {
            loggedTextView.setText("Welcome " + name);
        }

        loggedIn = isLogged;




        usernameEditText = findViewById(R.id.usernameEditText);


        button = findViewById((R.id.button1));
        myTextView = findViewById((R.id.textView));
        myEditText = findViewById((R.id.editText));

        mySpinner1 = findViewById(R.id.spinner1);
        mySpinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Unit_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Unit_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(adapter2);

        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Do something when an item is selected
                selectedItemFrom = (String) parent.getItemAtPosition(position);

                // Store the selected item in a variable or in a database
                // For example, you can store it in SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selectedItem", selectedItemFrom);
                editor.apply();
                myTextView.setText(getResources().getString(R.string.myTextBoxString) + " " +selectedItemFrom.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });

        mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Do something when an item is selected
                selectedItemTo= (String) parent.getItemAtPosition(position);

                // Store the selected item in a variable or in a database
                // For example, you can store it in SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selectedItem", selectedItemTo);
                editor.apply();
                myTextView.setText(getResources().getString(R.string.myTextBoxString) + " " +selectedItemTo.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] units = getResources().getStringArray(R.array.Unit_array);
                //Length units
                String cm = units[0];
                String inch = units[1];
                String foot = units[2];
                String yard = units[3];
                String mile = units[4];
                String km = units[5];

                //Weight units
                String kg = units[6];
                String pound = units[7];
                String ounce = units[8];
                String ton = units[9];

                //Temperature units
                String Celsius = units[10];
                String Fahrenheit = units[11];
                String Kelvin = units[12];
                insertNumber = Double.parseDouble(myEditText.getText().toString());
                String insert = selectedItemFrom;
                String output = selectedItemTo;

                //Length Conversation
                if (insert.contains(inch) && output.contains(cm)){
                    convertedNumber = insertNumber * 254/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(cm) && output.contains(inch)) {
                    convertedNumber = insertNumber * 39/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert == output) {
                    myTextView.setText("Error: Two same unit");
                    Toast.makeText(getApplicationContext(), "Error: Cannot convert from " + insert + " to " + output, Toast.LENGTH_SHORT).show(); // use Toast inside else condition
                } else if (insert.contains(foot) && output.contains(cm)) {
                    convertedNumber = insertNumber * 3048/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(cm) && output.contains(foot)) {
                    convertedNumber = insertNumber * 33/1000;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(yard) && output.contains(cm)) {
                    convertedNumber = insertNumber * 9144/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(cm) && output.contains(yard)) {
                    convertedNumber = insertNumber * 11/1000;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(mile) && output.contains(km)) {
                    convertedNumber = insertNumber * 1609/1000;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(km) && output.contains(mile)) {
                    convertedNumber = insertNumber * 62/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();

                //Weight Conversation
                } else if (insert.contains(pound) && output.contains(kg)) {
                    convertedNumber = insertNumber * 454/1000;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(ounce) && output.contains(kg)) {
                    convertedNumber = insertNumber * 2834/100;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(ton) && output.contains(kg)) {
                    convertedNumber = insertNumber * 907185/1000;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();

                //Temperature Conversation
                } else if (insert.contains(Celsius) && output.contains(Fahrenheit)) {
                    convertedNumber = insertNumber * 18/10 + 32;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(Fahrenheit) && output.contains(Celsius)) {
                    convertedNumber = (insertNumber - 32) / 1.8;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+new DecimalFormat("##.##").format(convertedNumber) + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(Celsius) && output.contains(Kelvin)) {
                    convertedNumber = insertNumber + 273.15;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                } else if (insert.contains(Kelvin) && output.contains(Celsius)) {
                    convertedNumber = insertNumber - 273.15;
                    myTextView.setText(insertNumber + selectedItemFrom.toString() + " = "+ convertedNumber + selectedItemTo.toString());
                    Toast.makeText(getApplicationContext(), "Success: Converted from " + insert + " to " + output, Toast.LENGTH_SHORT).show();
                }

                else {
                    myTextView.setText("Error: Invaild unit");
                    Toast.makeText(getApplicationContext(), "Error: Cannot convert from " + insert + " to " + output, Toast.LENGTH_SHORT).show(); // use Toast inside else condition
                }

            }
        });



    }
}

