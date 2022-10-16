package com.example.expensemobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddDetailsActivity extends AppCompatActivity {
    EditText amount_input, date_input;
    Spinner spinnerType;
    Button add_expense_db_button;
    String tripId;
    private final String[] expenseType = {
            "Transportation",
            "Food",
            "Accommodation"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        //Assign Variable
        amount_input = findViewById(R.id.amount_input);
        date_input = findViewById(R.id.date_input);
        spinnerType = findViewById(R.id.spinnerType);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenseType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter((dataAdapter));

        tripId = getIntent().getStringExtra("tripId");

        add_expense_db_button = findViewById(R.id.add_expense_db_button);

        add_expense_db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(AddDetailsActivity.this);
                String amount = amount_input.getText().toString().trim();
                String date = date_input.getText().toString().trim();
                String type = spinnerType.getSelectedItem().toString();
                Integer trip = Integer.valueOf(tripId);

                myDB.addExpense(trip, type, amount, date);
            }
        });
    }
}