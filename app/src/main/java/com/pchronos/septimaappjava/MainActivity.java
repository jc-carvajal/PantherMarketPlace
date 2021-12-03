package com.pchronos.septimaappjava;

import static android.view.Gravity.AXIS_SPECIFIED;
import static android.view.Gravity.CENTER;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView T1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        T1=(TextView) findViewById(R.id.t1home);
        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.apertura,R.string.cierre);
        toggle.syncState();
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.op1nav:
                T1.setText("Escogió opcion 1 crear usuario");
                //lanza la activity del formulario
                Intent AFR=new Intent(this,FormularioRegistro.class);
                startActivity(AFR);
                break;

            case R.id.op2nav:
                T1.setText("Comprar");
                break;

            case R.id.op5nav:
                T1.setText("Cerrar sesion");

                AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
                ALERTA.setTitle(R.string.menu_cerrar_sesion);
                ALERTA.setMessage(R.string.msg_confirmar_cerrar_sesion);
                ALERTA.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent SA2=new Intent(view.getContext(),Login.class);
                        //startActivity(SA2);
                        Intent RA=new Intent(getBaseContext(),Login.class);//revisar
                        startActivity(RA);
                    }
                });

                ALERTA.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                ALERTA.create().show();

                break;
        }
        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}