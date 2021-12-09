package com.pchronos.septimaappjava;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class pruebas extends AppCompatActivity {
    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";
    int op=0;
    LinearLayout CONTE;
    Button B1;
    Fragment FRAG;
    EditText E1,E2,E3,E4,E5,E6,P,PC;
    TextView T1;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR = new HashMap<>();
        CONTE=(LinearLayout)findViewById(R.id.contenedor);
        CONTE.setVisibility(View.INVISIBLE);
        B1=(Button)findViewById(R.id.b1);
        E1=(EditText)findViewById(R.id.e1);
        E2=(EditText)findViewById(R.id.e2);
        E3=(EditText)findViewById(R.id.e3);
        E4=(EditText)findViewById(R.id.e4);
        E5=(EditText)findViewById(R.id.e5);
        E6=(EditText)findViewById(R.id.e6);
        T1=(TextView)findViewById(R.id.t1);
        DB=new DatabaseHelper(this);
    }

    public void metodoPolitica(View view) {
        switch (op)
        {
            case 0:
                CONTE.setVisibility(View.VISIBLE);
                FRAG=new Fragmento1();
                op=1;
                cargarFragmento(FRAG);
                break;
            case 1:
                FRAG=new Fragmento2();
                op=2;
                cargarFragmento(FRAG);
                break;
            case 2:
                CONTE.setVisibility(View.INVISIBLE);
                op=0;
        }
    }

    private void cargarFragmento(Fragment frag)
    {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fragment,frag);
        transaction.commit();

    }

    public void crearUsuario(View view) {
        String nombre,apellido,telefono,documento,password,usuario;
        int v1,v2,v3;
        nombre=E1.getText().toString();
        apellido=E2.getText().toString();
        telefono=E3.getText().toString();
        documento=E4.getText().toString();
        password=E5.getText().toString();
        usuario=E6.getText().toString();
        DATOSALMACENAR.put("NOMBRE",nombre);
        DATOSALMACENAR.put("APELLIDO",apellido);
        DATOSALMACENAR.put("TELEFONO",telefono);
        DATOSALMACENAR.put("DOCUMENTO",documento);
        DATOSALMACENAR.put("PASSWORD",password);
        DATOSALMACENAR.put("USUARIO",usuario);
        DB_FIRE.collection("USUARIOS").document(usuario).set(DATOSALMACENAR);



    }
}
