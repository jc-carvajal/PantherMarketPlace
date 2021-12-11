package com.pchronos.septimaappjava;

import static android.view.Gravity.CENTER;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class FormularioRegistro extends AppCompatActivity {
    int op=0;
    LinearLayout CONTE;
    Button B1, B2;
    Fragment FRAG;
    EditText R_US,R_PASS,R_PASS2,R_NOMBRE,R_APE,R_TEL,R_EMAIL,P,Pc;
    TextView T1;
    DatabaseHelper DB;
    CheckBox CHECK;

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);
        CONTE=(LinearLayout) findViewById(R.id.contenedor);
        CONTE.setVisibility(View.INVISIBLE);
        B1=(Button) findViewById(R.id.b1);
        B2=(Button) findViewById(R.id.b2);
        R_US=(EditText)findViewById(R.id.reg_usuario);
        R_PASS=(EditText)findViewById(R.id.reg_pass);
        R_PASS2=(EditText)findViewById(R.id.reg_pass2);
        R_NOMBRE=(EditText)findViewById(R.id.reg_nombre);
        R_APE=(EditText)findViewById(R.id.reg_apellido);
        R_TEL=(EditText)findViewById(R.id.reg_telefono);
        R_EMAIL=(EditText)findViewById(R.id.reg_email);
        T1=(TextView) findViewById(R.id.testado);

        DB=new DatabaseHelper(this);
        CHECK=(CheckBox) findViewById(R.id.check_registro);

        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR= new HashMap<>();
    }


    public void metodopolitica(View view) {
        switch (op)
        {
            case 0:
                CONTE.setVisibility(View.INVISIBLE);
                FRAG=new Fragmento1();
                op=1;
                cargarFragmento(FRAG);
                CONTE.setVisibility(View.VISIBLE);
                break;

            case 1:
                CONTE.setVisibility(View.INVISIBLE);
                FRAG=new Fragmento2();
                op=2;
                cargarFragmento(FRAG);
                CONTE.setVisibility(View.VISIBLE);
                break;

            case 2:
                CONTE.setVisibility(View.INVISIBLE);
                op=0;

        }
    }


    private void cargarFragmento(Fragment frag)
    {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.fragment,frag);
        transaction.commit();
    }

    public void crearUsuario(View view) {
        String usuario,password,password2,nombre,apellido,telefono,correo;
        int v1,v2,v3,v4,v5,v6,v7;

        String DB_USUARIO="";

        usuario=R_US.getText().toString();
        v1=validarCaracteres(3,usuario);

        password=R_PASS.getText().toString();
        v2=validarCaracteres(3,password);

        password2=R_PASS2.getText().toString();
        v7=validarCaracteres(3,password2);

        nombre=R_NOMBRE.getText().toString();
        v3=validarCaracteres(1,nombre);

        apellido=R_APE.getText().toString();
        v4=validarCaracteres(1,apellido);

        telefono=R_TEL.getText().toString();
        v5=validarCaracteres(2,telefono);

        correo=R_EMAIL.getText().toString();
        v6=validarCaracteres(4,correo);

        if (usuario.equals("") || password.equals("") || password2.equals("") || nombre.equals("") || apellido.equals("") || telefono.equals("") || usuario.equals(""))//que no hayan espacios vacios
        {
            Toast toastcompletar=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastcompletar.setText(R.string.msg_error_reg_llenar_txt);
            toastcompletar.setGravity(CENTER,0,0);
            toastcompletar.show();
        }
        else
        {
            if (v1!=1)//usuario
            {
                Toast toastusuario=Toast.makeText(this,"",Toast.LENGTH_LONG);
                toastusuario.setText(R.string.msg_error_reg_user_txt);
                toastusuario.setGravity(CENTER,0,0);
                toastusuario.show();
            }
            else
            {
                if (v2!=1 || v7!=1)//password 1 y 2
                {
                    Toast toastpassword=Toast.makeText(this,"",Toast.LENGTH_LONG);
                    toastpassword.setText(R.string.msg_error_reg_pass_txt);
                    toastpassword.setGravity(CENTER,0,0);
                    toastpassword.show();
                }
                else
                {
                    if (v3!=1)//nombre
                    {
                        Toast toastnombre=Toast.makeText(this,"",Toast.LENGTH_LONG);
                        toastnombre.setText(R.string.msg_error_reg_name_txt);
                        toastnombre.setGravity(CENTER,0,0);
                        toastnombre.show();
                    }
                    else
                    {
                        if (v4!=1)//apellido
                        {
                            Toast toastapellido=Toast.makeText(this,"",Toast.LENGTH_LONG);
                            toastapellido.setText(R.string.msg_error_reg_apellido_txt);
                            toastapellido.setGravity(CENTER,0,0);
                            toastapellido.show();
                        }
                        else
                        {
                            if (v5!=1)//telefono
                            {
                                Toast toasttelefono=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                toasttelefono.setText(R.string.msg_error_reg_telefono_txt);
                                toasttelefono.setGravity(CENTER,0,0);
                                toasttelefono.show();
                            }
                            else
                            {
                                if (v6!=1)//correo
                                {
                                    Toast toastemail=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                    toastemail.setText(R.string.msg_error_reg_email_txt);
                                    toastemail.setGravity(CENTER,0,0);
                                    toastemail.show();
                                }
                                else
                                {
                                    if (password.equals(password2))//confirmar que contraseñas sean iguales
                                    {
                                        //ver si existe el usuario

                                        if (CHECK.isChecked())//si check está marcado
                                        {
                                            //=====metodo consulta

                                            DB_FIRE.collection("USERS").document(usuario)
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String DB_USUARIO,DB_USUARIO_CONFIRM;

                                                    try {
                                                        DB_USUARIO=(documentSnapshot.get("USER").toString());
                                                    }catch (Exception e)
                                                    {
                                                        DB_USUARIO="";
                                                    }


                                                    if (DB_USUARIO.equals(usuario))
                                                    {
                                                        Toast toastexisteusuario=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                        toastexisteusuario.setText(R.string.msg_error_existe_usuario);
                                                        toastexisteusuario.setGravity(CENTER,0,0);
                                                        toastexisteusuario.show();

                                                    }
                                                    else
                                                    {


                                                        //guardar datos

                                                        DATOSALMACENAR.put("USER",usuario);
                                                        DATOSALMACENAR.put("PASSWORD",password);
                                                        DATOSALMACENAR.put("NAME",nombre);
                                                        DATOSALMACENAR.put("LASTNAME",apellido);
                                                        DATOSALMACENAR.put("PHONENUMBER",telefono);
                                                        DATOSALMACENAR.put("EMAIL",correo);
                                                        DATOSALMACENAR.put("RECOVERY","");


                                                        try {

                                                            DB_FIRE.collection("USERS").document(usuario).set(DATOSALMACENAR);
                                                            //DB_FIRE.collection("USERS").document(usuario).update(DATOSALMACENAR);


                                                        }catch (Exception e)
                                                        {

                                                            Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                            toastregistroerror.setText(R.string.msg_registro_error_guardar);
                                                            toastregistroerror.setGravity(CENTER,0,0);
                                                            toastregistroerror.show();

                                                        }

                                                        //=======confirmar que se guardaron los datos
                                                        try {
                                                            DB_USUARIO_CONFIRM=(documentSnapshot.get("USER").toString());
                                                        }catch (Exception e)
                                                        {
                                                            DB_USUARIO_CONFIRM="";
                                                        }


                                                        if (DB_USUARIO_CONFIRM.equals(DB_USUARIO))
                                                        {
                                                            //Toast toastexisteusuario=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                            //toastexisteusuario.setText(R.string.msg_registro_exitoso);
                                                            //toastexisteusuario.setGravity(CENTER,0,0);
                                                            //toastexisteusuario.show();

                                                            lanzarAlertR();

                                                           /* AlertDialog.Builder ALERTA=new AlertDialog.Builder(getBaseContext());
                                                            ALERTA.setTitle(R.string.msg_registro_exitoso);
                                                            ALERTA.setMessage(R.string.msg_registro_a_login);
                                                            ALERTA.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent SA=new Intent(view.getContext(),Login.class);
                                                                    startActivity(SA);
                                                                }
                                                            });

                                                            ALERTA.create().show(); */



                                                        }
                                                        else
                                                        {
                                                            Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                            toastregistroerror.setText(R.string.msg_registro_error_guardar);
                                                            toastregistroerror.setGravity(CENTER,0,0);
                                                            toastregistroerror.show();
                                                        }
                                                        //=======confirmar que se guardaron los datos fin

                                                    }

                                                }
                                            });
                                            //=====metodo consulta fin
                                        }
                                        else
                                        {
                                            Toast toastaceptarpoliticas=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                            toastaceptarpoliticas.setText(R.string.msg_debe_aceptar);
                                            toastaceptarpoliticas.setGravity(CENTER,0,0);
                                            toastaceptarpoliticas.show();
                                        }


                                    }
                                    else
                                    {
                                        Toast toastpasscomparacion=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                        toastpasscomparacion.setText(R.string.msg_error_pass_comparacion);
                                        toastpasscomparacion.setGravity(CENTER,0,0);
                                        toastpasscomparacion.show();
                                        R_PASS.setText("");
                                        R_PASS2.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void crearUsuariosql(View view) {
        String usuario,password,password2,nombre,apellido,telefono,correo;
        int v1,v2,v3,v4,v5,v6,v7;

        usuario=R_US.getText().toString();
        v1=validarCaracteres(3,usuario);

        password=R_PASS.getText().toString();
        v2=validarCaracteres(3,password);

        password2=R_PASS2.getText().toString();
        v7=validarCaracteres(3,password2);

        nombre=R_NOMBRE.getText().toString();
        v3=validarCaracteres(1,nombre);

        apellido=R_APE.getText().toString();
        v4=validarCaracteres(1,apellido);

        telefono=R_TEL.getText().toString();
        v5=validarCaracteres(2,telefono);

        correo=R_EMAIL.getText().toString();
        v6=validarCaracteres(4,correo);

        if (usuario.equals("") || password.equals("") || password2.equals("") || nombre.equals("") || apellido.equals("") || telefono.equals("") || usuario.equals(""))//que no hayan espacios vacios
        {
            Toast toastcompletar=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastcompletar.setText(R.string.msg_error_reg_llenar_txt);
            toastcompletar.setGravity(CENTER,0,0);
            toastcompletar.show();
        }
        else
        {
            if (v1!=1)//usuario
            {
                Toast toastusuario=Toast.makeText(this,"",Toast.LENGTH_LONG);
                toastusuario.setText(R.string.msg_error_reg_user_txt);
                toastusuario.setGravity(CENTER,0,0);
                toastusuario.show();
            }
            else
            {
                if (v2!=1 || v7!=1)//password 1 y 2
                {
                    Toast toastpassword=Toast.makeText(this,"",Toast.LENGTH_LONG);
                    toastpassword.setText(R.string.msg_error_reg_pass_txt);
                    toastpassword.setGravity(CENTER,0,0);
                    toastpassword.show();
                }
                else
                {
                    if (v3!=1)//nombre
                    {
                        Toast toastnombre=Toast.makeText(this,"",Toast.LENGTH_LONG);
                        toastnombre.setText(R.string.msg_error_reg_name_txt);
                        toastnombre.setGravity(CENTER,0,0);
                        toastnombre.show();
                    }
                    else
                    {
                        if (v4!=1)//apellido
                        {
                            Toast toastapellido=Toast.makeText(this,"",Toast.LENGTH_LONG);
                            toastapellido.setText(R.string.msg_error_reg_apellido_txt);
                            toastapellido.setGravity(CENTER,0,0);
                            toastapellido.show();
                        }
                        else
                        {
                            if (v5!=1)//telefono
                            {
                                Toast toasttelefono=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                toasttelefono.setText(R.string.msg_error_reg_telefono_txt);
                                toasttelefono.setGravity(CENTER,0,0);
                                toasttelefono.show();
                            }
                            else
                            {
                                if (v6!=1)//correo
                                {
                                    Toast toastemail=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                    toastemail.setText(R.string.msg_error_reg_email_txt);
                                    toastemail.setGravity(CENTER,0,0);
                                    toastemail.show();
                                }
                                else
                                {
                                    if (password.equals(password2))//confirmar que contraseñas sean iguales
                                    {
                                        //ver si existe el usuario
                                        Cursor res= DB.getDataUsuario(usuario);
                                        String DATOS=null;
                                        if (res.moveToFirst())//ver si usuario existe
                                        {
                                            Toast toastexisteusuario=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                            toastexisteusuario.setText(R.string.msg_error_existe_usuario);
                                            toastexisteusuario.setGravity(CENTER,0,0);
                                            toastexisteusuario.show();
                                        }
                                        else
                                        {
                                            if (CHECK.isChecked())//si check está marcado
                                            {

                                                //guardar datos

                                                if(DB.insertData(usuario,password,nombre,apellido,telefono,correo))
                                                {
                                                    //T1.setText("Registro Exitoso");
                                                    //Toast toastregistroexitoso=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                                    //toastregistroexitoso.setText(R.string.msg_registro_exitoso);
                                                    //toastregistroexitoso.setGravity(CENTER,0,0);
                                                    //toastregistroexitoso.show();

                                                    AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
                                                    ALERTA.setTitle(R.string.msg_registro_exitoso);
                                                    ALERTA.setMessage(R.string.msg_registro_a_login);
                                                    ALERTA.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent SA=new Intent(view.getContext(),Login.class);
                                                            startActivity(SA);
                                                        }
                                                    });

                                                    ALERTA.create().show();

                                                }
                                                else
                                                {
                                                    //T1.setText("Error en Registro");
                                                    Toast toastregistroerror=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                                    toastregistroerror.setText(R.string.msg_registro_error_guardar);
                                                    toastregistroerror.setGravity(CENTER,0,0);
                                                    toastregistroerror.show();
                                                }
                                            }
                                            else
                                            {
                                                Toast toastaceptarpoliticas=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                                toastaceptarpoliticas.setText(R.string.msg_debe_aceptar);
                                                toastaceptarpoliticas.setGravity(CENTER,0,0);
                                                toastaceptarpoliticas.show();
                                            }
                                        }

                                    }
                                    else
                                    {
                                        Toast toastpasscomparacion=Toast.makeText(this,"",Toast.LENGTH_LONG);
                                        toastpasscomparacion.setText(R.string.msg_error_pass_comparacion);
                                        toastpasscomparacion.setGravity(CENTER,0,0);
                                        toastpasscomparacion.show();
                                        R_PASS.setText("");
                                        R_PASS2.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }




/*
        if (password.equals(password2))//confirmar que contraseñas sean iguales
        {

            if (v1!=1 || v2!=1 || v3!=1 || v4!=1 || v5!=1 || v6!=1)
            {
                //no se creará el usuario
                T1.setText("error");
            }
            else
            {
                //crear un usuario nuevo
                //T1.setText("ok");
                //guardar datos
                if(DB.insertData(usuario,password,nombre,apellido,telefono,correo));
                {
                    T1.setText("Registro Exitoso");
                }
            }

        }
        else
        {
            Toast toastpasscomparacion=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastpasscomparacion.setText(R.string.msg_error_pass_comparacion);
            toastpasscomparacion.setGravity(CENTER,0,0);
            toastpasscomparacion.show();
            R_PASS.setText("");
            R_PASS2.setText("");
        }*/





    }

    private int validarCaracteres(int i, String cadena) {
        int validacion=0,pos,c;
        switch(i)
        {
            case 1:
                //caracteres alfabeticos
                for(pos=0;pos<cadena.length();pos=pos+1)
                {
                    c=cadena.charAt(pos);
                    if((c>=67 && c<=90) || (c>=97 && c<=122))
                    {
                        validacion=1;

                    }
                    else
                    {
                        pos=cadena.length();
                        validacion=0;
                    }
                }
                break;

            case 2:
                //caracteres numericos
                for(pos=0;pos<cadena.length();pos=pos+1)
                {
                    c=cadena.charAt(pos);
                    if(c>=48 && c<=57)
                    {
                        validacion=1;

                    }
                    else
                    {
                        pos=cadena.length();
                        validacion=0;
                    }
                }
                break;

            case 3:
                //contraseñas y usuarios - Alfanumerico
                for(pos=0;pos<cadena.length();pos=pos+1)
                {
                    c=cadena.charAt(pos);
                    if((c>=48 && c<=57) || (c>=67 && c<=90) || (c>=97 && c<=122))
                    {
                        validacion=1;

                    }
                    else
                    {
                        pos=cadena.length();
                        validacion=0;
                    }
                }
                break;

            case 4:
                //correo - Alfanumerico + . + @ + - + _
                for(pos=0;pos<cadena.length();pos=pos+1)
                {
                    c=cadena.charAt(pos);
                    if((c>=48 && c<=57) || (c>=67 && c<=90) || (c>=97 && c<=122) || c==46 || c==64 || c==45 || c==95)
                    {
                        validacion=1;

                    }
                    else
                    {
                        pos=cadena.length();
                        validacion=0;
                    }
                }
                break;


        }
        return validacion;
    }

    private void lanzarAlertR() {


        androidx.appcompat.app.AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
        ALERTA.setTitle(R.string.msg_registro_exitoso);
        ALERTA.setMessage(R.string.msg_registro_a_login);
        ALERTA.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent SA2=new Intent(view.getContext(),Login.class);
                //startActivity(SA2);
                Intent RA=new Intent(getBaseContext(),Login.class);//revisar
                startActivity(RA);
            }
        });

        ALERTA.create().show();

    }

    public void metodopolitica2(View view) {

        AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
        ALERTA.setTitle(R.string.msg_usage_policies_titulo);
        ALERTA.setMessage(R.string.datos1);
        ALERTA.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ALERTA.setTitle(R.string.msg_usage_policies_titulo);
                ALERTA.setMessage(R.string.datos2);
                ALERTA.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ALERTA.create().show();


            }
        });

        ALERTA.create().show();
    }
}