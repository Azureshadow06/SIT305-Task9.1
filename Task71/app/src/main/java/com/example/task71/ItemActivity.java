package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    DatabaseHelper db;
    private ArrayList<User> userList;
    TextView itemNameTextView;
    TextView passwordTextView;
    TextView locationTextView;
    TextView describeTextView;
    TextView dateTextView;
    TextView typeTextView;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//This activity create a view of ItemActivity based on User's selection
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemNameTextView = findViewById(R.id.iNameTextView);
        passwordTextView = findViewById(R.id.iPasswordTextView);
        locationTextView = findViewById(R.id.iLocationTextView);
        describeTextView = findViewById(R.id.iDescribeTextView);
        dateTextView = findViewById(R.id.iDateTextView);
        typeTextView = findViewById(R.id.iTypeTextView);



        deleteButton = findViewById(R.id.buttonDelete);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        userList = new ArrayList<>();
        db = new DatabaseHelper(this);
        userList = db.readItem();
        User user = userList.get(position);
        itemNameTextView.setText("Item Name: " + user.getUsername() );
        passwordTextView.setText("Phone: " +user.getPassword());
        locationTextView.setText("Location: "+user.getLocation());
        describeTextView.setText("Description: "+user.getDescribe());
        dateTextView.setText("Date: "+user.getDate());
        typeTextView.setText("Type: "+user.getType());




        deleteButton.setOnClickListener(new View.OnClickListener() {//Click this button to delete current item from database
            @Override
            public void onClick(View view) {
                db.deleteUser(user.getUsername());
                userList.remove(position);

                Toast.makeText(ItemActivity.this, "User deleted" + position, Toast.LENGTH_SHORT).show();
                Intent listIntent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(listIntent);
            }
        });



    }
}