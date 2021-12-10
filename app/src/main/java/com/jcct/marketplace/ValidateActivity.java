package com.jcct.marketplace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ValidateActivity extends AppCompatActivity
{
    DatabaseHelper DB;
    TextView T21;
    Button B21, B22;
    EditText E21,E22;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        T21 = (TextView) findViewById(R.id.t21);
        B21 = (Button) findViewById(R.id.b21Login);
        B22 = (Button) findViewById(R.id.b22Login);
        E21 = (EditText) findViewById(R.id.e21Login);
        E22 = (EditText) findViewById(R.id.e22Login);
        DB = new DatabaseHelper(this);
    }

    public void validateData(View view)
    {
        String usua = E21.getText().toString();
        String pass = E22.getText().toString();
        Cursor res = DB.getDataUser(usua);
        String DATA = null;
        if (res.moveToFirst())
        {
            DATA = " " + res.getString(6);
            if (DATA.equals(pass))
            {
                //Launch activity Purchase
            }
            else
            {
                //Show error through emergent information
            }
        }
        else
        {
            //Indicate user nonexistent
        }

        if (E21.getText().toString().equals("Robin") && E22.getText().toString().equals("75063014"))
        {
            AlertDialog.Builder WARNING = new AlertDialog.Builder(this);
            WARNING.setTitle("WARNING");
            WARNING.setMessage("Welcome to PANTHER SHOP. Go ahead!");
            WARNING.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent WA = new Intent(view.getContext(), WelcomeActivity.class);
                    startActivity(WA);
                }
            });
            WARNING.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Toast toast = Toast.makeText(view.getContext(), "", Toast.LENGTH_LONG);
                    toast.setText("You have canceled!");
                    toast.show();
                }
            });
            WARNING.create().show();
        }
        else
        {
            T21.setText(R.string.invalidData);
            Toast toast = Toast.makeText(view.getContext(), "", Toast.LENGTH_LONG);
            toast.setText("Please check your personal Data");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void createUserMethod(View view)
    {
        Intent RA = new Intent(this, RegisterActivity.class);
        startActivity(RA);
    }
}