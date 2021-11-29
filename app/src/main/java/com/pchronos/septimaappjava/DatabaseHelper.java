package com.pchronos.septimaappjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="DB_GRUPO9";
    public final static String TABLE1_NAME="USUARIOS";
    public final static String COL1_1="ID";
    public final static String COL2_1="USUARIO";
    public final static String COL3_1="PASSWORD";
    public final static String COL4_1="NOMBRE";
    public final static String COL5_1="APELLIDO";
    public final static String COL6_1="TELEFONO";
    public final static String COL7_1="CORREO";

    public final static String TABLE2_NAME="COMPRAS";
    public final static String COL1_2="ID";
    public final static String COL2_2="USUARIO";
    public final static String COL3_2="ARTICULO";
    public final static String COL4_2="CANTIDAD";

    public final static String TABLE3_NAME="ULTIMOUSUARIO";
    public final static String COL_13="ID";
    public final static String COL_23="USUARIO";
    public final static String COL_33="SESION";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE1_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO TEXT,PASSWORD TEXT,NOMBRE TEXT,APELLIDO TEXT,TELEFONO INTEGER,CORREO TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE2_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO TEXT,ARTICULO TEXT,CANTIDAD INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE3_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO TEXT,SESION TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3_NAME);
        onCreate(db);
    }

    public boolean insertUltimoUsuario(String usuario,String sesion)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_23,usuario);
        cv.put(COL_33,sesion);
        long result=db.insert(TABLE3_NAME,null,cv);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updateUltimoUsuario(String usuario,String sesion)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_23,usuario);
        cv.put(COL_33,sesion);
        long result=db.update(TABLE3_NAME,cv,"ID=?",new String[]{"1"});
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insertData(String usuario,String password,String nombre,String apellido,String telefono,String correo)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL2_1,usuario);
        cv.put(COL3_1,password);
        cv.put(COL4_1,nombre);
        cv.put(COL5_1,apellido);
        cv.put(COL6_1,telefono);
        cv.put(COL7_1,correo);
        long result=db.insert(TABLE1_NAME,null,cv);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getDataUsuario(String usuario)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT *FROM "+TABLE1_NAME+" WHERE USUARIO='"+usuario+"'",null);
        return cursor;
    }

    public Cursor getUltimo(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //Cursor cursor=db.rawQuery("SELECT *FROM "+TABLE3_NAME+" WHERE ID='"+id+"'",null);
        Cursor cursor=db.rawQuery("SELECT *FROM "+TABLE3_NAME+" WHERE ID='"+id+"'",null);
        return cursor;
    }

    public Cursor getData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT *FROM "+TABLE1_NAME+" WHERE ID='"+id+"'",null);
        return cursor;
    }

    public boolean upDateData(String id, String usuario,String password,String nombre,String apellido,String telefono,String correo)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        //cv.put(COL2_1,usuario);
        cv.put(COL3_1,password);
        cv.put(COL4_1,nombre);
        cv.put(COL5_1,apellido);
        cv.put(COL6_1,telefono);
        cv.put(COL7_1,correo);
        long result=db.update(TABLE1_NAME,cv,"USUARIO=?",new String[]{usuario});
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean deleteData(String usuario)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE1_NAME,"USUARIO=?",new String[]{usuario});
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}