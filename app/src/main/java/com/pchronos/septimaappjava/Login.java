package com.pchronos.septimaappjava;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    DatabaseHelper DB;
    Button Bini,Breg;
    EditText USER,PASS;
    CheckBox CHECK_RU;
    TextView T1;

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        USER=(EditText) findViewById(R.id.inicio_usuario);
        PASS=(EditText) findViewById(R.id.inicio_password);
        CHECK_RU=(CheckBox) findViewById(R.id.check_recordar);
        Bini=(Button) findViewById(R.id.inicio_boton_log_in);
        Breg=(Button) findViewById(R.id.inicio_bonton_sign_in);
        T1=(TextView)findViewById(R.id.inicio_textView);
        DB=new DatabaseHelper(this);
        metodoSesion();

        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR = new HashMap<>();



    }

    public void iniciarSesion(View view) {
        String usuario=USER.getText().toString();
        String password=PASS.getText().toString();
        //Cursor res= DB.getDataUsuario(usuario);
        Cursor res2=DB.getUltimo("1");
        String DATOS=null;

        try {

            DB_FIRE.collection("USERS").document(usuario).get()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast toast=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                            toast.setText(R.string.msg_error_user_pass2);
                            toast.setGravity(CENTER,0,0);
                            toast.show();
                            PASS.setText("");

                        }
                    })
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //T1.setText(documentSnapshot.get("NOMBRE").toString()+"\n"+documentSnapshot.get("IDENTIFICACION").toString());
                        String BB="";
                        try {
                            BB=documentSnapshot.get("PASSWORD").toString();
                        }catch (Exception e)
                        {
                            BB="";
                        }

                        //T1.setText(documentSnapshot.get("PASSWORD").toString());

                    if (BB.equals(PASS.getText().toString()))
                    {
                        if(res2.moveToFirst())//actualizar ultimo usuario si existe
                        {
                            if(CHECK_RU.isChecked()==true)//guardar ultimo usuario
                            {
                                //DB.insertUltimoUsuario(USER.getText().toString(),"1");
                                DB.updateUltimoUsuario(USER.getText().toString(),"1");
                                //USNAME.setText("funciona");


                            }
                            if(CHECK_RU.isChecked()==false)
                            {
                                DB.updateUltimoUsuario(USER.getText().toString(),"0");
                                //USNAME.setText("funciona");
                            }
                        }
                        else//guardar ultimo usuario si no existe
                        {
                            if(CHECK_RU.isChecked()==true)
                            {
                                //DB.insertUltimoUsuario(USER.getText().toString(),"1");
                                DB.insertUltimoUsuario(USER.getText().toString(),"1");
                                //USNAME.setText("funciona");


                            }
                            if(CHECK_RU.isChecked()==false)
                            {
                                DB.insertUltimoUsuario(USER.getText().toString(),"0");
                                //USNAME.setText("funciona");
                            }
                        }


                        //Lanzar la activity
                        Intent SA=new Intent(view.getContext(),MainActivity.class);
                        startActivity(SA);
                        PASS.setText("");


                    }
                    else
                    {
                        //Si la contraseña es incorrecta
                        Toast toast=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        toast.setText(R.string.msg_error_user_pass2);
                        toast.setGravity(CENTER,0,0);
                        toast.show();
                        PASS.setText("");
                    }

                    }





                });

        }catch (Exception e){

            Toast toast=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
            toast.setText(R.string.msg_error_user_pass2);
            toast.setGravity(CENTER,0,0);
            toast.show();
            PASS.setText("");

        }



    }



    public void iniciarSesionSQL(View view) {
        String usua=USER.getText().toString();
        String pass=PASS.getText().toString();
        Cursor res= DB.getDataUsuario(usua);
        Cursor res2=DB.getUltimo("1");
        String DATOS=null;
        if (res.moveToFirst())
        {
            DATOS=""+res.getString(2);//PASSWORD se coloca 2 porque se inicia a contar desde 0
            if (DATOS.equals(pass))
            {
                if(res2.moveToFirst())//actualizar ultimo usuario si existe
                {
                    if(CHECK_RU.isChecked()==true)//guardar ultimo usuario
                    {
                        //DB.insertUltimoUsuario(USER.getText().toString(),"1");
                        DB.updateUltimoUsuario(USER.getText().toString(),"1");


                    }
                    if(CHECK_RU.isChecked()==false)
                    {
                        DB.updateUltimoUsuario(USER.getText().toString(),"0");
                    }
                }
                else//guardar ultimo usuario si no existe
                {
                    if(CHECK_RU.isChecked()==true)
                    {
                        //DB.insertUltimoUsuario(USER.getText().toString(),"1");
                        DB.insertUltimoUsuario(USER.getText().toString(),"1");


                    }
                    if(CHECK_RU.isChecked()==false)
                    {
                        DB.insertUltimoUsuario(USER.getText().toString(),"0");
                    }
                }




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


    public void metodoSesion2(View view) {
        Cursor res=DB.getUltimo("1");
        String DATOS=null;

        if(res.moveToFirst())
        {
            //USER.setText(res.getString(1));
            //CHECK_RU.setChecked(true);

        }
    }

    public void metodoSesion() {
        Cursor res=DB.getUltimo("1");
        String DATOS=null;


        if(res.moveToFirst())
        {
            //T1.setText(res.getString(2));
            if (res.getString(2).equals("0"))
            {
                USER.setText("");
                CHECK_RU.setChecked(false);
            }
            else
            {
                if (res.getString(2).equals("1"))
                {
                    USER.setText(res.getString(1));
                    CHECK_RU.setChecked(true);
                }
            }




        }
        /*else
        {
            USER.setText("");
            CHECK_RU.setChecked(false);
        }*/
    }

    public void crearusuariofirebase(View view) {
        Intent FR=new Intent(this,pruebas.class);
        startActivity(FR);
    }

    public void recuperarPassword(View view) {
        Intent FP=new Intent(this,Recuperar.class);
        startActivity(FP);
    }
}