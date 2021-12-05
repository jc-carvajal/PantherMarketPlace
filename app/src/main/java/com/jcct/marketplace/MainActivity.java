package com.jcct.marketplace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectRegister(View view)
    {
        AlertDialog.Builder WARNING = new AlertDialog.Builder(this);
        WARNING.setTitle(R.string.advise);
        WARNING.setMessage(R.string.datos1);
        WARNING.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent RA = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(RA);
            }
        });
        WARNING.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast toast = Toast.makeText(view.getContext(), "", Toast.LENGTH_LONG);
                toast.setText("You Canceled");
                toast.show();
            }
        });
        WARNING.create().show();
        /*Intent RA = new Intent(view.getContext(), RegisterActivity.class);
        startActivity(RA);*/
    }

    public void selectLogin(View view)
    {
        Intent LA = new Intent(view.getContext(), ValidateActivity.class);
        startActivity(LA);
    }
}