package com.jcct.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ValidateActivity extends AppCompatActivity
{
    Button B21;
    EditText E21,E22;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        B21 = (Button) findViewById(R.id.b11);
        E21 = (EditText) findViewById(R.id.e21);
        E22 = (EditText) findViewById(R.id.e22);
    }

    public void validateData(View view)
    {
        if (E21.getText().toString().equals("Robin") && E22.getText().toString().equals("75063014"))
        {
            Intent WA = new Intent(view.getContext(), WelcomeActivity.class);
            startActivity(WA);
        }
    }
}