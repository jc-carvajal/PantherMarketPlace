package com.pchronos.septimaappjava;

import static android.view.Gravity.CENTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Configuracion extends AppCompatActivity {
    EditText OLD_PASS,NEW_PASS,NEW_PASS2;
    TextView T1;

    DatabaseHelper DB;

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        OLD_PASS=(EditText) findViewById(R.id.old_pass);
        NEW_PASS=(EditText) findViewById(R.id.new_pass);
        NEW_PASS2=(EditText) findViewById(R.id.new_pass2);
        T1=(TextView)findViewById(R.id.t1);

        DB=new DatabaseHelper(getBaseContext());

        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR = new HashMap<>();


    }

    public void cambiarPassword(View view) {
        String old_password,new_password,new_password2;
        int v1,v2,v3;


        //====obtener usuario desde el ultimo usuario de base de datos interna

        Cursor res=DB.getUltimo("1");
        String usuario="";
        String DATOS=null;


        if(res.moveToFirst())
        {
            DATOS=(res.getString(1));
            //T1.setText(usuario);
            usuario=DATOS;

        }

        //====obtener usuario desde el ultimo usuario de base de datos interna fin

        old_password=OLD_PASS.getText().toString();
        v1=validarCaracteres2(3,old_password);

        new_password=NEW_PASS.getText().toString();
        v2=validarCaracteres2(3,new_password);

        new_password2=NEW_PASS2.getText().toString();
        v3=validarCaracteres2(3,new_password2);


        if (old_password.equals("") || new_password.equals("") || new_password2.equals(""))
        {
            Toast toastcompletar=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastcompletar.setText(R.string.msg_error_pass_change_llenar_txt);
            toastcompletar.setGravity(CENTER,0,0);
            toastcompletar.show();
        }
        else
        {
            if (v1!=1 || v2!=1 || v3!=1)//password 1 y 2
            {
                Toast toastpassword=Toast.makeText(this,"",Toast.LENGTH_LONG);
                toastpassword.setText(R.string.msg_error_reg_pass_txt);
                toastpassword.setGravity(CENTER,0,0);
                toastpassword.show();
            }
            else
            {
                if (new_password.equals(new_password2))//confirmar que contraseñas sean iguales
                {


                    try {


                        //===bloque de consulta firebase

                        String finalUsuario = usuario;
                        DB_FIRE.collection("USERS").document(usuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        //T1.setText(documentSnapshot.get("NOMBRE").toString()+"\n"+documentSnapshot.get("IDENTIFICACION").toString());
                                        String BB="";
                                        String US= finalUsuario;
                                        //String FB_NEWPASSWORD_CONFIRM;
                                        try {
                                            BB=documentSnapshot.get("PASSWORD").toString();
                                        }catch (Exception e)
                                        {
                                            BB="";
                                        }

                                        //T1.setText(documentSnapshot.get("PASSWORD").toString());

                                        if (BB.equals(old_password))
                                        {

                                            DATOSALMACENAR.put("PASSWORD",new_password);

                                            //====Guardar nueva contraseña

                                            try {

                                                //DB_FIRE.collection("USERS").document(usuario).set(DATOSALMACENAR);
                                                DB_FIRE.collection("USERS").document(finalUsuario).update(DATOSALMACENAR);


                                            }catch (Exception e)
                                            {

                                                Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                toastregistroerror.setText(R.string.msg_error_guardar);
                                                toastregistroerror.setGravity(CENTER,0,0);
                                                toastregistroerror.show();

                                            }

                                            //====Guardar nueva contraseña fin

                                            //=======confirmar que se guardaron los datos
                                            String FB_NEWPASSWORD_CONFIRM;
                                            try {
                                                FB_NEWPASSWORD_CONFIRM=(documentSnapshot.get("PASSWORD").toString());
                                            }catch (Exception e)
                                            {
                                                FB_NEWPASSWORD_CONFIRM="";
                                            }


                                            if (FB_NEWPASSWORD_CONFIRM.equals(old_password))
                                            {

                                                Toast toastexisteusuario=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                toastexisteusuario.setText(R.string.msg_change_pass_exitoso);
                                                toastexisteusuario.setGravity(CENTER,0,0);
                                                toastexisteusuario.show();

                                                OLD_PASS.setText("");
                                                NEW_PASS.setText("");
                                                NEW_PASS2.setText("");


                                            }
                                            else
                                            {
                                                Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                                toastregistroerror.setText(R.string.msg_error_guardar);
                                                toastregistroerror.setGravity(CENTER,0,0);
                                                toastregistroerror.show();
                                            }
                                            //=======confirmar que se guardaron los datos fin



                                        }
                                        else
                                        {
                                            //Si la contraseña es incorrecta
                                            Toast toast=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                            toast.setText(R.string.msg_error_user_pass3);
                                            toast.setGravity(CENTER,0,0);
                                            toast.show();
                                            OLD_PASS.setText("");
                                        }

                                    }





                                });

                        //===bloque de consulta firebase fin

                    }catch (Exception e)
                    {
                        Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        toastregistroerror.setText(R.string.msg_error_guardar);
                        toastregistroerror.setGravity(CENTER,0,0);
                        toastregistroerror.show();
                    }


                }
                else
                {
                    Toast toastpasscomparacion=Toast.makeText(this,"",Toast.LENGTH_LONG);
                    toastpasscomparacion.setText(R.string.msg_error_new_pass_comparacion);
                    toastpasscomparacion.setGravity(CENTER,0,0);
                    toastpasscomparacion.show();
                    NEW_PASS.setText("");
                    NEW_PASS2.setText("");
                }
            }
        }
    }

    private int validarCaracteres2(int i, String cadena) {
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
}