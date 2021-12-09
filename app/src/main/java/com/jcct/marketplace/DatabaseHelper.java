package com.jcct.marketplace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public final static String DATABASE_NAME = "DB_PRUEBA";
    public final static String TABLE_NAME = "TABLE1";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "LAST_NAME";
    public final static String COL_4 = "EMAIL";
    public final static String COL_5 = "PHONE_NUMBER";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, LAST_NAME TEXT, EMAIL TEXT, PHONE_NUMBER INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String name, String last_name, String email, String phone_number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, last_name);
        cv.put(COL_4, email);
        cv.put(COL_5, phone_number);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM " + TABLE_NAME + " WHERE ID = '"+id+"'", null);
        return cursor;
    }

    public boolean upDateData(String id, String name, String last_name, String email, String phone_number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, last_name);
        cv.put(COL_4, email);
        cv.put(COL_5, phone_number);
        long result = db.update(TABLE_NAME, cv, "ID = ?", new String[]{String.valueOf(id)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean deleteData (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
