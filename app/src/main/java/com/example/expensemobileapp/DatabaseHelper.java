package com.example.expensemobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Context context;
    public static final String DATABASE_NAME="ExpenseManagement.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "trips";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESTINATION="destination";
    public static final String COLUMN_DATE="trip_date";
    public static final String COLUMN_ASSESSMENT="trip_assessment";
    public static final String COLUMN_DESCRIPTION="trip_description";

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

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTrip(String name, String destination, String date, String assessment, String description){
        ContentValues newRowValue = new ContentValues();


        newRowValue.put(COLUMN_NAME, name);
        newRowValue.put(COLUMN_DESTINATION, destination);
        newRowValue.put(COLUMN_DATE, date);
        newRowValue.put(COLUMN_ASSESSMENT, assessment);
        newRowValue.put(COLUMN_DESCRIPTION, description);

        long result = database.insert(TABLE_NAME, null, newRowValue);
        if(result != -1){
            Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}
