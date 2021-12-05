package com.jcct.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectLogin(View view)
    {
        Intent LA = new Intent(view.getContext(), ValidateActivity.class);
        startActivity(LA);
    }

    public void selectRegister(View view)
    {
        Intent RA = new Intent(view.getContext(), RegisterActivity.class);
        startActivity(RA);
    }
}