package com.example.expensemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddTripActivity extends AppCompatActivity {
    EditText trip_name_input, trip_destination_input, trip_date_input, trip_description_input, assessment_choice_input;
    Button add_db_button;

    boolean isAllFieldsChecked = false;

    private boolean CheckAllFields() {
        if (trip_name_input.length() == 0) {
            trip_name_input.setError("This field is required");
            return false;
        }
        if (trip_destination_input.length() == 0) {
            trip_destination_input.setError("This field is required");
            return false;
        }
        if (trip_date_input.length() == 0) {
            trip_date_input.setError("This field is required");
            return false;
        }

        if (assessment_choice_input.length() == 0) {
            assessment_choice_input.setError("This field is required");
            return false;
        }


        // after all validation return true.
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        //Assign Variable
        trip_name_input = findViewById(R.id.trip_name_input);
        trip_destination_input = findViewById(R.id.trip_destination_input);
        trip_date_input = findViewById(R.id.trip_date_input);
        trip_description_input = findViewById(R.id.trip_description_input);
        assessment_choice_input = findViewById(R.id.assessment_choice_input);
        add_db_button = findViewById(R.id.add_db_button);



        add_db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    DatabaseHelper myDB = new DatabaseHelper(AddTripActivity.this);
                    String name = trip_name_input.getText().toString().trim();
                    String destination = trip_destination_input.getText().toString().trim();
                    String date = trip_date_input.getText().toString().trim();
                    String description = trip_description_input.getText().toString().trim();
                    String assessment = assessment_choice_input.getText().toString().trim();

                    myDB.addTrip(name, destination, date, assessment, description);
                }
                }
            //}
        });
    }
}