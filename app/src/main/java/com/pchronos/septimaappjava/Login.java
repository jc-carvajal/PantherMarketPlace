package com.pchronos.septimaappjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    Button Bini,Breg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bini=(Button) findViewById(R.id.inicio_boton_log_in);
    }

    public void iniciarSesion(View view) {

            Intent SA=new Intent(view.getContext(),MainActivity.class);
            startActivity(SA);

    }

    public void crearUsuario(View view) {
        Intent AFR=new Intent(this,FormularioRegistro.class);
        startActivity(AFR);
    }
}