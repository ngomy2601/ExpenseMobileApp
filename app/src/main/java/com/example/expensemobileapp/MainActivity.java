package com.example.expensemobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DatabaseHelper myDB;

    ArrayList<String> _id, name, destination, trip_date, trip_assessment, trip_description;
    CustomAdapter customAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);

        _id = new ArrayList<>();
        name = new ArrayList<>();
        destination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_assessment = new ArrayList<>();
        trip_description = new ArrayList<>();

        storeTripsDataInArray();

        customAdapter = new CustomAdapter(MainActivity.this, this, _id, name, destination, trip_date, trip_assessment, trip_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeTripsDataInArray() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "You must add data in database", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }

        if (item.getItemId() == R.id.search) {

            SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    

                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                myDB.deleteAllData();

                //Render again
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}