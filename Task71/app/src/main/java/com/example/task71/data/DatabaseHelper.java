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
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    public long insertUser(User user)
    {
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

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Util.USERNAME + "=?", new String[]{String.valueOf(username)});
        db.close();
    }




    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }



    // we have created a new method for reading all the courses.
    public ArrayList<User> readCourses()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<User> courseModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                courseModalArrayList.add(new User(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(6),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

}
