package com.pchronos.septimaappjava;

import static android.view.Gravity.CENTER;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper DB;
    Button Bini,Breg;
    EditText USER,PASS;
    CheckBox REM_US;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        USER=(EditText) findViewById(R.id.inicio_usuario);
        PASS=(EditText) findViewById(R.id.inicio_password);
        REM_US=(CheckBox) findViewById(R.id.inicio_recordar);
        Bini=(Button) findViewById(R.id.inicio_boton_log_in);
        Breg=(Button) findViewById(R.id.inicio_bonton_sign_in);
        DB=new DatabaseHelper(this);


    }

    public void iniciarSesion(View view) {
        String usua=USER.getText().toString();
        String pass=PASS.getText().toString();
        Cursor res= DB.getDataUsuario(usua);
        String DATOS=null;
        if (res.moveToFirst())
        {
            DATOS=""+res.getString(2);//PASSWORD se coloca 2 porque se inicia a contar desde 0
            if (DATOS.equals(pass))
            {
                //Lanzar la activity
                Intent SA=new Intent(view.getContext(),MainActivity.class);
                startActivity(SA);
                PASS.setText("");
            }
            else
            {
                //indicar error a travez de informacion emergente
                Toast toast=Toast.makeText(this,"",Toast.LENGTH_LONG);
                toast.setText(R.string.msg_error_user_pass2);
                toast.setGravity(CENTER,0,0);
                toast.show();
                PASS.setText("");
            }
        }
        else
        {
            //indicar error a travez de informacion emergente
            Toast toast=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toast.setText(R.string.msg_error_user_pass);
            toast.setGravity(CENTER,0,0);

            toast.show();
        }
    }

    public void iniciarSesionbypass(View view) {

        Intent SA=new Intent(view.getContext(),MainActivity.class);
        startActivity(SA);

    }

    public void crearUsuario(View view) {
        Intent AFR=new Intent(this,FormularioRegistro.class);
        startActivity(AFR);
    }

    public void vaciarpass(View view) {
        //PASS.setText("");
    }
}