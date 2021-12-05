package com.jcct.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity
{
    EditText E41,E42,E43,E44;
    Button B41,B42;
    TextView T41;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        E41 = (EditText) findViewById(R.id.e41);
        E42 = (EditText) findViewById(R.id.e42);
        E43 = (EditText) findViewById(R.id.e43);
        E44 = (EditText) findViewById(R.id.e44);
        B41 = (Button) findViewById(R.id.b41);
        B42 = (Button) findViewById(R.id.b42);
        T41 = (TextView) findViewById(R.id.t41);
    }

    public void createUser(View view)
    {
        String name, last_name, email, phone_number;
        int v1, v2, v3, v4;
        name = E41.getText().toString();
        v1 = validateCharacters(1, name);
        last_name = E42.getText().toString();
        v2 = validateCharacters(1, last_name);
        email = E43.getText().toString();
        v3 = validateCharacters(2, email);
        phone_number = E44.getText().toString();
        v4 = validateCharacters(2,phone_number);

        if ((v1 != 1) || (v2 != 1) || (v3 != 1) || (v4 != 1))
        {
            //Not will be created the User
            T41.setText(R.string.error);
        }
        else
        {
            //Create new User
            T41.setText(R.string.ok);
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
                    if ((c >= 67 && c <= 90) || (c >= 97 && c <= 122))
                    {
                        validation = 1;
                    }
                    else
                    {
                        pos = chain.length();
                        validation = 0;
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
}