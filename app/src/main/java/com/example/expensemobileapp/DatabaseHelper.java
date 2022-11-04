package com.example.expensemobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Context context;
    public static final String DATABASE_NAME = "ExpenseManagement.db";
    public static final int DATABASE_VERSION = 1;

    //Trips Table
    public static final String TABLE_NAME = "trips";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DATE = "trip_date";
    public static final String COLUMN_ASSESSMENT = "trip_assessment";
    public static final String COLUMN_DESCRIPTION = "trip_description";

    //Expenses Table
    public static final String EXPENSE_TABLE_NAME = "expenses";
    public static final String COLUMN_EXPENSE_ID = "_expenseId";
    public static final String COLUMN_TRIP_ID = "tripId";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIME = "time";

    //Create TRIPS TABLE
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_DESTINATION, COLUMN_DATE, COLUMN_ASSESSMENT, COLUMN_DESCRIPTION
    );

    //Create EXPENSES TABLE
    private static final String EXPENSE_DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "CONSTRAINT fk_trips FOREIGN KEY (%s) REFERENCES %s (%s) )",
            EXPENSE_TABLE_NAME, COLUMN_EXPENSE_ID, COLUMN_TRIP_ID, COLUMN_TYPE, COLUMN_AMOUNT, COLUMN_TIME, COLUMN_EXPENSE_ID, TABLE_NAME, COLUMN_ID
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(EXPENSE_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(db);
    }

    //START - CRUD Trips
    public void addTrip(String name, String destination, String date, String assessment, String description) {
        ContentValues newRowValue = new ContentValues();


        newRowValue.put(COLUMN_NAME, name);
        newRowValue.put(COLUMN_DESTINATION, destination);
        newRowValue.put(COLUMN_DATE, date);
        newRowValue.put(COLUMN_ASSESSMENT, assessment);
        newRowValue.put(COLUMN_DESCRIPTION, description);

        long result = database.insert(TABLE_NAME, null, newRowValue);
        if (result != -1) {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateTripData(String row_id, String name, String destination, String date, String assessment, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newRowValue = new ContentValues();


        newRowValue.put(COLUMN_NAME, name);
        newRowValue.put(COLUMN_DESTINATION, destination);
        newRowValue.put(COLUMN_DATE, date);
        newRowValue.put(COLUMN_ASSESSMENT, assessment);
        newRowValue.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, newRowValue, "_id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    Cursor searchData(String keyword) {
        String query = "SELECT * FROM trips " + "WHERE name LIKE '%" + keyword + "%'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    //END - CRUD Trips

    //START - CRUD Expenses
    public void addExpense(Integer tripId, String type, String amount, String time) {
        ContentValues newRowValue = new ContentValues();


        newRowValue.put(COLUMN_TRIP_ID, tripId);
        newRowValue.put(COLUMN_TYPE, type);
        newRowValue.put(COLUMN_AMOUNT, amount);
        newRowValue.put(COLUMN_TIME, time);

        long result = database.insert(EXPENSE_TABLE_NAME, null, newRowValue);
        if (result != -1) {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllExpenseData(String id) {
        String query = "SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE " + COLUMN_TRIP_ID + " LIKE '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    //END - CRUD Expenses
}
