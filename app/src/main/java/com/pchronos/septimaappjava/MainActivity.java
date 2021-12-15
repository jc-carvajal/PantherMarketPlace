package com.pchronos.septimaappjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    TextView T1;
    TextView US_NAME;
    DatabaseHelper DB;

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //T1=(TextView) findViewById(R.id.t1home);
        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.apertura,R.string.cierre);
        toggle.syncState();
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR= new HashMap<>();

        DB=new DatabaseHelper(this);
        US_NAME=(TextView) navigationView.getHeaderView(0).findViewById(R.id.dbname1);
        metodousname();
    }

    public void metodousname()
    {
        Cursor res=DB.getUltimo("1");
        String DATOS=null;
        String usuario;

        if(res.moveToFirst())
        {
            //US_NAME.setText(res.getString(1));
            usuario=res.getString(1);
        }
        else
        {
            usuario="";
        }

        try
        {
            DB_FIRE.collection("USERS").document(usuario).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        //T1.setText(documentSnapshot.get("NOMBRE").toString()+"\n"+documentSnapshot.get("IDENTIFICACION").toString());
                        String BB="";
                        try
                        {
                            BB=documentSnapshot.get("NAME").toString();
                        }
                        catch (Exception e)
                        {
                            BB="";
                        }
                        US_NAME.setText(BB);
                    }
                });
        }
        catch (Exception e) {}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.op1nav:
                //T1.setText("Escogió opcion 1 crear usuario");
                //lanza la activity del formulario
                Intent AFR=new Intent(this,Configuracion.class);
                startActivity(AFR);
                break;
            case R.id.op2nav:
                //T1.setText("Comprar");
                Intent CH=new Intent(this, HomeShoppingActivity.class);
                startActivity(CH);
                break;

            case R.id.op4nav:
                //T1.setText("Comprar");
                Intent AB=new Intent(this, AboutActivity.class);
                startActivity(AB);
                break;

            case R.id.op5nav:
                //T1.setText("Cerrar sesion");
                AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
                ALERTA.setTitle(R.string.menu_cerrar_sesion);
                ALERTA.setMessage(R.string.msg_confirmar_cerrar_sesion);
                ALERTA.setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Intent SA2=new Intent(view.getContext(),Login.class);
                        //startActivity(SA2);
                        Intent RA=new Intent(getBaseContext(),Login.class);//revisar
                        startActivity(RA);
                    }
                });
                ALERTA.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

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