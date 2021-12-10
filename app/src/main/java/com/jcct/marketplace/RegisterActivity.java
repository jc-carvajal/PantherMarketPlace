package com.jcct.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity
{
    EditText E40,E41,E42,E43,E44,E45;
    Button B41,B42,B43,B44,B45;
    TextView T41;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        E40 = (EditText) findViewById(R.id.e40);
        E41 = (EditText) findViewById(R.id.e41);
        E42 = (EditText) findViewById(R.id.e42);
        E43 = (EditText) findViewById(R.id.e43);
        E44 = (EditText) findViewById(R.id.e44);
        E45 = (EditText) findViewById(R.id.e45);
        B41 = (Button) findViewById(R.id.b41);
        B42 = (Button) findViewById(R.id.b42);
        B43 = (Button) findViewById(R.id.b43);
        B44 = (Button) findViewById(R.id.b44);
        B45 = (Button) findViewById(R.id.b45);
        T41 = (TextView) findViewById(R.id.t41);
        DB= new DatabaseHelper(this);
    }

    public void createUser(View view)
    {
        //Ingresar usuario y password
        String user, name, last_name, email, phone_number, password;
        int v1, v2, v3, v4, v5, v6;
        user = E40.getText().toString();
        v1 = validateCharacters(1,user);
        name = E41.getText().toString();
        v2 = validateCharacters(1, name);
        last_name = E42.getText().toString();
        v3 = validateCharacters(1, last_name);
        email = E43.getText().toString();
        v4 = validateCharacters(1, email);
        phone_number = E44.getText().toString();
        v5 = validateCharacters(1,phone_number);
        password = E45.getText().toString();
        v6 = validateCharacters(1,phone_number);

        if ((v1 != 1) || (v2 != 1) || (v3 != 1) || (v4 != 1) || (v5 != 1)|| (v6 != 1))
        {
            //Not will be created the User
            T41.setText(R.string.error);
        }
        else
        {
            //Create new User
            Cursor res = DB.getDataUser(user);//Ckeck whether this argument is proper!!!
            String DATA = null;
            if (res.moveToFirst())
            {
                DATA = " " + res.getString(6);
                if (DATA.equals(user))
                {
                    //Error message 'user existent'
                }
                else
                {
                    DB.insertData(user, name, last_name, email, phone_number, password);
                    T41.setText(R.string.msj_crated_user);
                }
            }
        }
    }

    private int validateCharacters(int i, String chain)
    {
        int validation = 0;
        int pos;
        int c;

        switch (i)
        {
            case 1:
                //Alphabet characters
                for (pos = 0; pos < chain.length(); pos ++)
                {
                    c = chain.charAt(pos);
                    if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122))
                    {
                        validation = 1;
                    }
                    else
                    {
                        validation = 0;

                        pos = chain.length();
                    }
                }
                break;

            case 2:
                //Numerical characters
                for (pos = 0; pos < chain.length(); pos ++)
                {
                    c = chain.charAt(pos);
                    if (c < 48 || c > 57)
                    {
                        validation = 0;
                        pos = chain.length();
                    }
                    else
                    {
                        validation = 1;
                        c = chain.length();
                    }
                }
                break;
        }
        return validation;
    }

    public void logOut(View view)
    {
        Intent MA = new Intent(view.getContext(), MainActivity.class);
        startActivity(MA);
    }

    public void consultMethod(View view)
    {
        //Searching Criteria
        Cursor res = DB.getData(E44.getText().toString());
        String DATA = null;
        if (res.moveToFirst())
        {
            DATA = "ID: " + res.getString(0) + "\n NAME: " + res.getString(1)
                    + "\n LAST_NAME: " + res.getString(2) + "\n EMAIL: " + res.getString(3) +
                    "\n PHONE_NUMBER: " + res.getString(4);
        }
        T41.setText(DATA);
    }

    public void saveMethod(View view)
    {
        DB.insertData(E40.getText().toString(), E41.getText().toString(), E42.getText().toString(),
                E43.getText().toString(), E44.getText().toString(), E45.toString().toString());
    }

    public void updateMethod(View view)
    {
        DB.upDateData(E44.getText().toString(), E40.getText().toString(), E41.getText().toString(),
                E42.getText().toString(),E43.getText().toString(), E44.getText().toString(), E45.getText().toString());
        /*DB.upDateData(E44.getText().toString(), E41.getText().toString(), E42.getText().toString(),
                E43.getText().toString());*/
    }

    public void deleteMethod(View view)
    {
        DB.deleteData(E44.getText().toString());
    }
}