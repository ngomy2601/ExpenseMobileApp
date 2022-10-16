package com.example.expensemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListExpenses extends AppCompatActivity {
    RecyclerView recyclerView2;
    FloatingActionButton add_expense_button;
    String tripId;

    DatabaseHelper myDB;
    ArrayList<String> _id, type, amount, date;
    ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);
        recyclerView2 = findViewById(R.id.recyclerView2);

        tripId = getIntent().getStringExtra("tripId");
        add_expense_button = findViewById(R.id.add_expense_button);

        add_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListExpenses.this, AddDetailsActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(ListExpenses.this);

        _id = new ArrayList<>();
        type = new ArrayList<>();
        amount = new ArrayList<>();
        date = new ArrayList<>();

        storeExpensesDataInArray();

        expenseAdapter = new ExpenseAdapter(ListExpenses.this, this, _id, type, amount, date);
        recyclerView2.setAdapter(expenseAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(ListExpenses.this));
    }

    void storeExpensesDataInArray() {
        Cursor cursor = myDB.readAllExpenseData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "You must add data in database", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                _id.add(cursor.getString(0));
                type.add(cursor.getString(2));
                amount.add(cursor.getString(3));
                date.add(cursor.getString(4));
            }
        }
    }
}