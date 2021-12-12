package com.pchronos.septimaappjava;

import static android.view.Gravity.CENTER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Recuperar extends AppCompatActivity {
    String correo,contraseña;
    TextView T1,MAIL1,MAIL2;


    EditText USER,NEW_PASS_R,NEW_PASS_R2,REC_CODE;

    Session session;//esta usa las librerias

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        //mensaje=(EditText) findViewById(R.id.mensaje);
        //enviar=(Button) findViewById(R.id.enviar);
        T1=(TextView) findViewById(R.id.t1);
        MAIL1=(TextView) findViewById(R.id.mail1);
        MAIL2=(TextView) findViewById(R.id.mail2);
        USER=(EditText) findViewById(R.id.rec_usuario);
        NEW_PASS_R=(EditText)findViewById(R.id.rec_pass1);
        NEW_PASS_R2=(EditText)findViewById(R.id.rec_pass2);
        REC_CODE=(EditText)findViewById(R.id.rec_codigo);

        correo="shopteampanther@gmail.com";
        contraseña="PJRHL2021";

        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR= new HashMap<>();


    }

    public String coderandom(View view) {
        double A,B,C,D,E;
        int AA,BB,CC,DD,EE;
        String CODE,SA,SB,SC,SD,SE,USU;

        A=Math.random()*10;
        AA=(int)A;
        SA=String.valueOf(AA);

        B=Math.random()*10;
        BB=(int)B;
        SB=String.valueOf(BB);

        C=Math.random()*10;
        CC=(int)C;
        SC=String.valueOf(CC);

        D=Math.random()*10;
        DD=(int)D;
        SD=String.valueOf(DD);

        E=Math.random()*10;
        EE=(int)E;
        SE=String.valueOf(EE);

        CODE=SA+SB+SC+SD+SE;
        //T1.setText(CODE);

        if(USER.getText().toString().equals(""))
        {
            Toast toastcompletar=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastcompletar.setText(R.string.fill_user);
            toastcompletar.setGravity(CENTER,0,0);
            toastcompletar.show();
        }
        else
        {
            USU=guardarRecovery(CODE);
        }




        //enviarCorreo(CODE,USU);

        return CODE;
    }



    public void enviarCorreo(String CD,String MAIL) {
        String CODE=CD;
        String MSJ1,MSJ2,EMAIL;
        MSJ1=MAIL1.getText().toString();
        MSJ2=MAIL2.getText().toString();
        String mensaje;
        mensaje=MSJ1+CODE+MSJ2;
        //T1.setText(mensaje);
        //EMAIL="chrono80@hotmail.com";
        EMAIL=MAIL;


        correo="shopteampanther@gmail.com";
        contraseña="PJRHL2021";

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try {

            //session=Session
            session=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,contraseña);
                }
            });

            if(session!=null)
            {
                Message message=new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Recuperación de contraseña");
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EMAIL));

                message.setContent(mensaje.toString(),"text/html; charset=utf-8");
                Transport.send(message);
            }

        }catch (Exception e)
        {

        }

    }

    public String guardarRecovery(String CD) {
        String CODE=CD;
        String usuario=USER.getText().toString();


        //===bloque de consulta firebase

        String finalUsuario = usuario;
        DB_FIRE.collection("USERS").document(usuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //T1.setText(documentSnapshot.get("NOMBRE").toString()+"\n"+documentSnapshot.get("IDENTIFICACION").toString());
                String BB="";
                String US= finalUsuario;
                //String FB_NEWPASSWORD_CONFIRM;

                DATOSALMACENAR.put("RECOVERY",CODE);

                try {
                    DB_FIRE.collection("USERS").document(finalUsuario).update(DATOSALMACENAR);
                }catch (Exception e)
                {

                }

                lanzarAlert1();

                try {
                    BB=documentSnapshot.get("EMAIL").toString();
                }catch (Exception e)
                {
                    BB="";
                }



                enviarCorreo(CODE,BB);


        }});



            //===bloque de consulta firebase fin
        return "";
    }


    public void cambiarPassword(View view) {
        String recovery_code,new_password,new_password2;
        int v1,v2,v3;


        //====obtener usuario desde el ultimo usuario de base de datos interna


        String usuario=USER.getText().toString();


        //====obtener usuario desde el ultimo usuario de base de datos interna fin

        recovery_code=REC_CODE.getText().toString();
        //v1=validarCaracteres2(3,old_password);

        new_password=NEW_PASS_R.getText().toString();
        v2=validarCaracteres2(3,new_password);

        new_password2=NEW_PASS_R2.getText().toString();
        v3=validarCaracteres2(3,new_password2);


        if (recovery_code.equals("") || new_password.equals("") || new_password2.equals(""))
        {
            Toast toastcompletar=Toast.makeText(this,"",Toast.LENGTH_LONG);
            toastcompletar.setText(R.string.msg_error_pass_change_llenar_txt);
            toastcompletar.setGravity(CENTER,0,0);
            toastcompletar.show();
        }
        else
        {
            if (v2!=1 || v3!=1)//password 1 y 2
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
                                    BB=documentSnapshot.get("RECOVERY").toString();
                                }catch (Exception e)
                                {
                                    BB="";
                                }

                                //T1.setText(documentSnapshot.get("PASSWORD").toString());

                                if (BB.equals(recovery_code))
                                {

                                    DATOSALMACENAR.put("PASSWORD",new_password);
                                    DATOSALMACENAR.put("RECOVERY","");

                                    //====Guardar nueva contraseña

                                    try {

                                        //DB_FIRE.collection("USERS").document(usuario).set(DATOSALMACENAR);
                                        DB_FIRE.collection("USERS").document(finalUsuario).update(DATOSALMACENAR);

                                        REC_CODE.setText("");
                                        NEW_PASS_R.setText("");
                                        NEW_PASS_R2.setText("");

                                        lanzarAlert();


                                    }catch (Exception e)
                                    {

                                        Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                        toastregistroerror.setText(R.string.msg_error_guardar);
                                        toastregistroerror.setGravity(CENTER,0,0);
                                        toastregistroerror.show();

                                    }



                                    //====Guardar nueva contraseña fin

                                    /*
                                    //=======confirmar que se guardaron los datos
                                    String FB_NEWPASSWORD_CONFIRM2,FB_NEWPASSWORD_CONFIRM3,FB_NEWPASSWORD_CONFIRM;
                                    try {
                                        FB_NEWPASSWORD_CONFIRM2=(documentSnapshot.get("PASSWORD").toString());
                                        FB_NEWPASSWORD_CONFIRM3=(documentSnapshot.get("PASSWORD").toString());
                                        FB_NEWPASSWORD_CONFIRM=(documentSnapshot.get("PASSWORD").toString());
                                    }catch (Exception e)
                                    {
                                        FB_NEWPASSWORD_CONFIRM="";
                                    }




                                    if (FB_NEWPASSWORD_CONFIRM.equals(NEW_PASS_R.getText().toString()))
                                    {

                                        //Toast toastexisteusuario=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                        //toastexisteusuario.setText(R.string.msg_change_pass_exitoso);
                                        //toastexisteusuario.setGravity(CENTER,0,0);
                                        //toastexisteusuario.show();

                                        REC_CODE.setText("");
                                        NEW_PASS_R.setText("");
                                        NEW_PASS_R2.setText("");

                                        lanzarAlert();


                                    }
                                    else
                                    {
                                        Toast toastregistroerror=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                        toastregistroerror.setText(R.string.msg_error_guardar);
                                        toastregistroerror.setGravity(CENTER,0,0);
                                        toastregistroerror.show();
                                    }
                                    //=======confirmar que se guardaron los datos fin
                                    */



                                }
                                else
                                {
                                    //Si la contraseña es incorrecta
                                    Toast toast=Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                                    toast.setText(R.string.msg_error_rec_code);
                                    toast.setGravity(CENTER,0,0);
                                    toast.show();
                                    REC_CODE.setText("");
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
                    NEW_PASS_R.setText("");
                    NEW_PASS_R2.setText("");
                }
            }
        }
    }



    public void recuperarPassword(View view) {
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

    private void lanzarAlert1() {

        androidx.appcompat.app.AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
        ALERTA.setTitle(R.string.msg_codigo_generado);
        ALERTA.setMessage(R.string.msg_codigo_generado_mail);
        ALERTA.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                //Intent SA2=new Intent(view.getContext(),Login.class);
                //startActivity(SA2);
                Intent RA=new Intent(getBaseContext(),MainActivity.class);//revisar
                startActivity(RA);
                */
            }
        });



        ALERTA.create().show();

    }

    private void lanzarAlert() {

        androidx.appcompat.app.AlertDialog.Builder ALERTA=new AlertDialog.Builder(this);
        ALERTA.setTitle(R.string.msg_registro_exitoso);
        ALERTA.setMessage(R.string.msg_change_pass_exitoso);
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
}