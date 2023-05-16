package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//This activity create a view of SignupActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText sUsernameEditText = findViewById(R.id.sUsernameEditText);
        EditText sPasswordEditText = findViewById(R.id.sPasswordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        EditText sLocationEditText = findViewById(R.id.sLocationEditText);
        EditText sDescribeEditText = findViewById(R.id.description_edittext);
        EditText sDateEditText = findViewById(R.id.editTextDate);
        RadioGroup typeRadioGroup = findViewById(R.id.TypeRadioGroup);
        Button saveButton = findViewById(R.id.saveButton);
        Button locateButton = findViewById(R.id.buttonLocate);


        //Following code used to get user's realtime location from location activity, Set location into EditText
        Intent intent = getIntent();
        String formattedLocation = intent.getStringExtra("formattedLocation");
        sLocationEditText.setText(formattedLocation);

        db = new DatabaseHelper(this);

        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locateIntent = new Intent(SignupActivity.this, LocationActivity.class);
                startActivity(locateIntent);
            }
        });




        saveButton.setOnClickListener(new View.OnClickListener() {//Press this button to upload the info of lost&found item to database
            @Override
            public void onClick(View view) {
                String username = sUsernameEditText.getText().toString();
                String password = sPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String location = sLocationEditText.getText().toString();
                String describe = sDescribeEditText.getText().toString();
                String date = sDateEditText.getText().toString();
                String type = "";
                int selection = typeRadioGroup.getCheckedRadioButtonId();
                if (selection == R.id.foundRadioButton)
                {
                    type = "Found";
                } else if (selection == R.id.lostRadioButton) {
                    type = "Lost";
                }



                if(password.equals(confirmPassword)) //Let user Confirm their phone number by inserting one more time
                {
                    long result = db.insertUser(new User(username, password, location, describe, date, type));//Upload item to database by using insertUser method
                    if (result > 0)
                    {
                        Intent locateIntent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(locateIntent);
                        Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Registered Error", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignupActivity.this, "The passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}