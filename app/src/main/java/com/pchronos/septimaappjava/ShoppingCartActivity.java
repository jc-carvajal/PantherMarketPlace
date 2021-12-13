package com.pchronos.septimaappjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity
{
    //android.content.Context Context3;
    FirebaseFirestore DB_PANTHER;
    //Map<String, Object> DOCTOLOAD;
    String TAG;

    List<VGProduct> ShoppingCartList;

    TextView TxtvCountProducts;
    RecyclerView RV_ProductsList;
    Spinner SpinStates, SpinTowns;
    //EditText DeliveryAddress;
    Button BtnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        getSupportActionBar().hide();

        ShoppingCartList = new ArrayList<>();
        //   ------- AQUÍ SE DESENCADENA UN ERROR -------
        // Problemas para traer ShoppingCartList a ShoppingCartActivity
        // ShoppingCartList = (List<VGProduct>) getIntent().getSerializableExtra("ShoppingCart");
        DB_PANTHER = FirebaseFirestore.getInstance();
        StartProductsList();

        RV_ProductsList = (RecyclerView) findViewById(R.id.recyclervList2);
        //RV_ProductsList.setHasFixedSize(true);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(Context3);
        RV_ProductsList.setLayoutManager(new GridLayoutManager(ShoppingCartActivity.this, 1));
        ShoppingCardAdapter Adapter = new ShoppingCardAdapter(ShoppingCartActivity.this, ShoppingCartList);
        RV_ProductsList.setAdapter(Adapter);

        TxtvCountProducts = (TextView) findViewById(R.id.txtvCountProducts2);
        //DeliveryAddress = (EditText) findViewById(R.id.etxtDeliveryAddress);

        SpinStates = (Spinner) findViewById(R.id.spinStates);
        REFRESH_STATES_SPIN(SpinStates);
        SpinStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                REFRESH_TOWNS_SPIN(view);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        SpinTowns = (Spinner) findViewById(R.id.spinTowns);
        BtnBuy = (Button) findViewById(R.id.btnBuy);
        BtnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { }
        });
    }

    // Generar lista de productos en carrito con consulta a Firebase
    private void StartProductsList()
    {
        ShoppingCartList = new ArrayList<>();

        DB_PANTHER.collection("GAMES")
            .whereEqualTo("CATEGORY", "Sports").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    int i = 0;
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        Log.d(TAG, DOC.getId() + " => " + DOC.getData());
                        ShoppingCartList.add(new VGProduct(
                                DOC.get("IDGAME", Integer.class),
                                DOC.get("CATEGORYID", Integer.class),
                                DOC.get("YEAR", Integer.class),
                                DOC.get("PRICE", Integer.class),
                                DOC.get("INVENTARY", Integer.class),
                                DOC.getString(getString(R.string.DB_Games_Title)),
                                DOC.getString(getString(R.string.DB_Games_Descr)),
                                DOC.getString("CODIMAGE"),
                                DOC.getString(getString(R.string.DB_Categ)),
                                i,false));
                        i++;
                    }
                    Toast.makeText(getApplicationContext(),
                            ShoppingCartList.size() + " Products",Toast.LENGTH_SHORT).show();
                }
            });
        //   ------- AQUÍ SE DESENCADENA UN ERROR -------
        //ShoppingCardAdapter Adapter = new ShoppingCardAdapter(ShoppingCartActivity.this , ShoppingCartList);
        //RV_ProductsList.setAdapter(Adapter);
    }

    private void REFRESH_STATES_SPIN(View view)
    {
        List<String> ARRAYTOLIST = new ArrayList<String>();
        DB_PANTHER.collection("STATES").orderBy("STATE").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocuments)
                {
                    for (QueryDocumentSnapshot DOC : queryDocuments)
                    {
                        ARRAYTOLIST.add("" + DOC.get("STATE"));
                    }
                    String DATATOLIST[] = ARRAYTOLIST.toArray(new String[ARRAYTOLIST.size()]);
                    ArrayAdapter<String> ADAPTER = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, DATATOLIST);
                    ADAPTER.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    SpinStates.setAdapter(ADAPTER);
                }
            })
            .addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {

                }
            });
    }

    public void REFRESH_TOWNS_SPIN(View view)
    {
        List<String> ARRAYTOLIST = new ArrayList<String>();
        DB_PANTHER.collection("TOWNS")
            .whereEqualTo("STATE", SpinStates.getSelectedItem().toString()).get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocuments)
                {
                    for (QueryDocumentSnapshot DOC : queryDocuments)
                    {
                        //ARRAYTOLIST.add(DOC.get("IDTOWN") + ": " + DOC.get("TOWN") + " - " + DOC.get("STATE"));
                        ARRAYTOLIST.add("" +  DOC.get("TOWN"));
                    }
                    String DATATOLIST[] = ARRAYTOLIST.toArray(new String[ARRAYTOLIST.size()]);
                    ArrayAdapter<String> ADAPTER = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, DATATOLIST);
                    ADAPTER.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    SpinTowns.setAdapter(ADAPTER);
                    Toast.makeText(view.getContext(), ARRAYTOLIST.size() + " Towns", Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {

                }
            });
    }

}