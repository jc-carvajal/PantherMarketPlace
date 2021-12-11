package com.pchronos.septimaappjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePurchaseActivity extends AppCompatActivity
{
    FirebaseFirestore DB_PANTHER;
    Map<String, Object> DOCTOLOAD;
    Spinner SpinCategories;
    Button BtnPurchase;

    RecyclerView ProductsList;
    List<VGProduct> Products;
    android.content.Context Context;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_purchase);

        DB_PANTHER=FirebaseFirestore.getInstance();
        BtnPurchase=(Button)findViewById(R.id.btnPurchase);

        SpinCategories = (Spinner) findViewById(R.id.spin_categories);
        REFRESH_CATEGORIES_LIST(SpinCategories);
        SpinCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (SpinCategories.getSelectedItemPosition() == 0) StartProductsList();
                else RefreshtProductslist();
                StartProductsAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ProductsList = (RecyclerView) findViewById(R.id.recyclervList);
        ProductsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Context);
        ProductsList.setLayoutManager(layoutManager);
        ProductCardAdapter Adapter = new ProductCardAdapter(Products);
        StartProductsList();
        StartProductsAdapter();

    }

    private void REFRESH_CATEGORIES_LIST(View view)
    {
        List<String> ARRAYTOLIST = new ArrayList<>();
        DB_PANTHER.collection("CATEGORIES").orderBy("IDCATEGORY").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    ARRAYTOLIST.add(getString(R.string.All_Categ));
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        ARRAYTOLIST.add("" + DOC.get(getString(R.string.DB_Categ)));
                    }
                    String DATATOLIST[] = ARRAYTOLIST.toArray(new String[ARRAYTOLIST.size()]);
                    ArrayAdapter<String> ADAPTER = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, DATATOLIST);
                    ADAPTER.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    SpinCategories.setAdapter(ADAPTER);
                }
            })
            .addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    // Toast
                }
            });
    }

    private void StartProductsList()
    {
        Products = new ArrayList<>();
        DB_PANTHER.collection("GAMES").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        Log.d(TAG, DOC.getId() + " => " + DOC.getData());
                        Products.add(new VGProduct(DOC.get("IDGAME", Integer.class),
                                DOC.get("CATEGORYID", Integer.class),
                                DOC.get("YEAR", Integer.class),
                                DOC.get("PRICE", Integer.class),
                                DOC.get("INVENTARY", Integer.class),
                                DOC.getString(getString(R.string.DB_Games_Title)),
                                DOC.getString(getString(R.string.DB_Games_Descr)),
                                DOC.getString("CODIMAGE")));
                    }
                    Toast.makeText(getApplicationContext(),
                            Products.size() + " Products",Toast.LENGTH_SHORT).show();
                    StartProductsAdapter();
                }
                });
    }

    private void RefreshtProductslist()
    {
        Products = new ArrayList<>();
        String FieldToQuery = getString(R.string.DB_Categ);
        String ValueToQuery = SpinCategories.getSelectedItem().toString();

        DB_PANTHER.collection("GAMES")
            .whereEqualTo(FieldToQuery, ValueToQuery).get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        Log.d(TAG, DOC.getId() + " => " + DOC.getData());
                        Products.add(new VGProduct(DOC.get("IDGAME", Integer.class),
                                DOC.get("CATEGORYID", Integer.class),
                                DOC.get("YEAR", Integer.class),
                                DOC.get("PRICE", Integer.class),
                                DOC.get("INVENTARY", Integer.class),
                                DOC.getString(getString(R.string.DB_Games_Title)),
                                DOC.getString(getString(R.string.DB_Games_Descr)),
                                DOC.getString("CODIMAGE")));
                    }
                    Toast.makeText(getApplicationContext(),
                            Products.size() + " Products",Toast.LENGTH_SHORT).show();
                    StartProductsAdapter();
                }
            });
    }

    private void StartProductsAdapter()
    {
        ProductCardAdapter Adapter = new ProductCardAdapter(Products);
        ProductsList.setAdapter(Adapter);
    }

    public void LaunchProductDetailsActivity(View view)
    {
        Intent IntentProductDetails = new Intent(this, ProductDetailsActivity.class);
        startActivity(IntentProductDetails);
    }
}