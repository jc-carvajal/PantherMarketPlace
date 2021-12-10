package com.jcct.marketplace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public final static String DATABASE_NAME = "DB_PANTHER SHOP";
    public final static String TABLE1_NAME = "USERS";
    public final static String TABLE2_NAME = "PURCHASE";
    public final static String COL1_1 = "ID";
    public final static String COL2_1 = "USER";
    public final static String COL3_1 = "NAME";
    public final static String COL4_1 = "LAST_NAME";
    public final static String COL5_1 = "EMAIL";
    public final static String COL6_1 = "PHONE_NUMBER";
    public final static String COL7_1 = "PASSWORD";

    //TABLE II
    public final static String COL1_2 = "ID";
    public final static String COL2_2 = "USER";
    public final static String COL3_2 = "ITEM";
    public final static String COL4_2 = "QUANTITY";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE1_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " USER TEXT, NAME TEXT, LAST_NAME TEXT, EMAIL TEXT, PHONE_NUMBER INTEGER, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE2_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " USER TEXT, ITEM TEXT, QUANTITY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertData (String user, String name, String last_name, String email, String phone_number, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2_1, user);
        cv.put(COL3_1, name);
        cv.put(COL4_1, last_name);
        cv.put(COL5_1, email);
        cv.put(COL6_1, phone_number);
        cv.put(COL7_1, password);
        long result = db.insert(TABLE1_NAME, null, cv);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getDataUser (String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM " + TABLE1_NAME + " WHERE USER = '" + user + "'", null);
        return cursor;
    }

    public Cursor getData (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM " + TABLE1_NAME + " WHERE ID = '"+id+"'", null);
        return cursor;
    }

    public boolean upDateData (String id, String user, String name, String last_name, String email, String phone_number, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2_1, user);
        cv.put(COL3_1, name);
        cv.put(COL4_1, last_name);
        cv.put(COL5_1, email);
        cv.put(COL6_1, phone_number);
        cv.put(COL7_1, password);
        long result = db.update(TABLE1_NAME, cv, "ID = ?",new String[]{String.valueOf(id)});
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
        long result = db.delete(TABLE1_NAME, "ID = ?", new String[]{id});
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
