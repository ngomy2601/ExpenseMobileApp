package com.example.expensemobileapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchTripActivity extends AppCompatActivity {
    RecyclerView recyclerView3;
    EditText search_input;
    Button search_submit;
    DatabaseHelper myDB;

    ArrayList<String> _id, name, destination, trip_date, trip_assessment, trip_description;
    CustomAdapter customAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        recyclerView3 = findViewById(R.id.recyclerView3);
        search_input = findViewById(R.id.search_input);
        search_submit = findViewById(R.id.search_db_button);

        search_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new DatabaseHelper(SearchTripActivity.this);

                _id = new ArrayList<>();
                name = new ArrayList<>();
                destination = new ArrayList<>();
                trip_date = new ArrayList<>();
                trip_assessment = new ArrayList<>();
                trip_description = new ArrayList<>();

                storeSearchedTripsDataInArray(search_input.getText().toString());

                customAdapter1 = new CustomAdapter(SearchTripActivity.this, SearchTripActivity.this, _id, name, destination, trip_date, trip_assessment, trip_description);
                recyclerView3.setAdapter(customAdapter1);
                recyclerView3.setLayoutManager(new LinearLayoutManager(SearchTripActivity.this));
            }
        });
    }

    void storeSearchedTripsDataInArray(String keyword) {
        Cursor cursor = myDB.searchData(keyword);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                _id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_assessment.add(cursor.getString(4));
                trip_description.add(cursor.getString(5));
            }
        }
    }
}