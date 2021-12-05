package com.jcct.marketplace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ValidateActivity extends AppCompatActivity
{
    TextView T21;
    Button B21;
    EditText E21,E22;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        T21 = (TextView) findViewById(R.id.t21);
        B21 = (Button) findViewById(R.id.b11);
        E21 = (EditText) findViewById(R.id.e21);
        E22 = (EditText) findViewById(R.id.e22);
    }

    public void validateData(View view)
    {
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
}