package com.example.expensemobileapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText trip_name_input, trip_destination_input, trip_date_input, trip_description_input, assessment_choice_input;
    Button update_db_button, delete_db_button;

    String _id, name, destination, trip_date, trip_assessment, trip_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Assign variable
        trip_name_input = findViewById(R.id.trip_name_input2);
        trip_destination_input = findViewById(R.id.trip_destination_input2);
        trip_date_input = findViewById(R.id.trip_date_input2);
        trip_description_input = findViewById(R.id.trip_description_input2);
        assessment_choice_input = findViewById(R.id.assessment_choice_input2);
        update_db_button = findViewById(R.id.update_db_button);
        delete_db_button = findViewById(R.id.delete_db_button);
        //Should be here if you want to render data again after updating
        getAndSetIntentData();

        //Set ActionBar title = Trip Name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                String name = trip_name_input.getText().toString().trim();
                String destination = trip_destination_input.getText().toString().trim();
                String date = trip_date_input.getText().toString().trim();
                String description = trip_description_input.getText().toString().trim();
                String assessment = assessment_choice_input.getText().toString().trim();
                myDB.updateTripData(_id, name, destination, date, description, assessment);
            }
        });
        delete_db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
        //Should be here if you do not want to render data again after updating
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("_id")
                && getIntent().hasExtra("Trip Name")
                && getIntent().hasExtra("Trip Destination")
                && getIntent().hasExtra("Trip Date")
                && getIntent().hasExtra("Trip Description")
                && getIntent().hasExtra("Trip Assessment")) {

            //Getting Data from Intent
            _id = getIntent().getStringExtra("_id");
            name = getIntent().getStringExtra("Trip Name");
            destination = getIntent().getStringExtra("Trip Destination");
            trip_date = getIntent().getStringExtra("Trip Date");
            trip_assessment = getIntent().getStringExtra("Trip Assessment");
            trip_description = getIntent().getStringExtra("Trip Description");

            //Setting Intent Data
            trip_name_input.setText(name);
            trip_destination_input.setText(destination);
            trip_date_input.setText(trip_date);
            trip_description_input.setText(trip_description);
            assessment_choice_input.setText(trip_assessment);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(_id);
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