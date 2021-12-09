package com.pchronos.septimaappjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ComprasHome extends AppCompatActivity {

    FirebaseFirestore DB_FIRE;
    Map<String, Object> DATOSALMACENAR;
    Button B1;
    TextView T1;
    String ARTICULO,TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_home2);
        DB_FIRE=FirebaseFirestore.getInstance();
        DATOSALMACENAR = new HashMap<>();
        CONSULTA();
        T1=(TextView)findViewById(R.id.t1Compras);
        B1=(Button)findViewById(R.id.b1Compras);
    }

    private void CONSULTA() {
        /*DB_FIRE.collection("ROUTERS").document("R1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                T1.setText(documentSnapshot.get("NOMBRE").toString()+"\n"+documentSnapshot.get("DESCRIPCION").toString());
                ARTICULO=documentSnapshot.getId().toString();//consultar ID

            }
        });*/

        DB_FIRE.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                T1.setText(document.getData().toString());//usar un arraylist
                                ARTICULO=document.getId().toString();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    public void metodoComprar(View view) {
        DATOSALMACENAR.put("ARTICULO",ARTICULO);

        DB_FIRE.collection("COMPRAS").document("email@email.com");
    }
}