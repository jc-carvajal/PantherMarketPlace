package com.pchronos.septimaappjava;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.TextView;

import java.io.StringWriter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView T1;
    TextView US_NAME;


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

        //US_NAME=(TextView)findViewById(R.id.dbname1);
        //metodousname();
    }

    public void metodousname() {
        US_NAME.setText("funciona");
        

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.op1nav:
                T1.setText("Escogió opcion 1 crear usuario");
                //lanza la activity del formulario
                Intent AFR=new Intent(this,Configuraciones.class);
                startActivity(AFR);

                break;

            case R.id.op2nav:
                T1.setText("Comprar");
                Intent CH=new Intent(this,ComprasHome.class);
                startActivity(CH);
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