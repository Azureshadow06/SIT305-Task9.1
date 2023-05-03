package com.example.task71;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<User> staffList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        staffList = new ArrayList<>();
        db = new DatabaseHelper(ListActivity.this);

        staffList = db.readCourses();

        recyclerViewAdapter = new RecyclerViewAdapter(staffList, ListActivity.this);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

    }
}