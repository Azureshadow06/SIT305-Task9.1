package com.example.task71.data;

import static com.example.task71.util.Util.DATABASE_VERSION;
import static com.example.task71.util.Util.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.task71.model.User;
import com.example.task71.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Declare DatabaseHelper class to access SQLite database function in activities
    public DatabaseHelper(@Nullable Context context) {
        //Initialize the SQLiteOpenHelper superclass with the necessary parameters to create
        //and manage the database.
        super(context, Util.DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Generate SQLite table based on the number of variables and their format.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USERNAME + " TEXT,"
                + Util.PASSWORD + " TEXT,"
                + Util.DESCRIBE + " TEXT,"
                + Util.DATE + " TEXT,"
                + Util.TYPE + " TEXT,"
                + Util.LOCATION + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Upgrade database by DROP TABLE automatically when Version number change
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    public long insertUser(User user)
    {   //Declare a long function to add new item into array.
        //Also, return long value to check the result of execution
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getPassword());
        contentValues.put(Util.LOCATION, user.getLocation());
        contentValues.put(Util.DESCRIBE, user.getDescribe());
        contentValues.put(Util.DATE, user.getDate());
        contentValues.put(Util.TYPE, user.getType());
        long newRowId = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public void deleteUser(String name){ //Delete Item from database based on input name of item
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Util.USERNAME + "=?", new String[]{String.valueOf(name)});
        db.close();
    }




    public void clearDatabase() { //Clear all the item in database
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }



    public ArrayList<User> readItem()  { // Declare the arraylist function to read all data from database and output them
        SQLiteDatabase db = this.getReadableDatabase();

        //Creating a cursor with query to read data from database.
        Cursor cursorItems
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<User> itemModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorItems.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                itemModalArrayList.add(new User(
                        cursorItems.getString(1),
                        cursorItems.getString(2),
                        cursorItems.getString(6),
                        cursorItems.getString(3),
                        cursorItems.getString(4),
                        cursorItems.getString(5)));
            } while (cursorItems.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorItems.close();
        return itemModalArrayList;
    }

}
