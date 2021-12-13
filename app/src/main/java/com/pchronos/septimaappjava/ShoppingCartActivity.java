package com.pchronos.septimaappjava;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity
{
    android.content.Context Context;
    FirebaseFirestore DB_PANTHER;
    //Map<String, Object> DOCTOLOAD;
    String TAG;

    RecyclerView RV_ProductsList;
    TextView TxtvCountProducts;
    Button BtnBuy;

    List<VGProduct> ShoppingCartList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        DB_PANTHER = FirebaseFirestore.getInstance();
        TxtvCountProducts = (TextView) findViewById(R.id.txtvCountProducts);
        BtnBuy = (Button) findViewById(R.id.btnBuy);

        RV_ProductsList = (RecyclerView) findViewById(R.id.recyclervList);
        RV_ProductsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Context);
        RV_ProductsList.setLayoutManager(layoutManager);

        // StartProductsList();
        // StartProductsAdapter();
    }
}