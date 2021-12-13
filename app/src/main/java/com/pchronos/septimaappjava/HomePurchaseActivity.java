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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePurchaseActivity extends AppCompatActivity
{
    android.content.Context Context;
    FirebaseFirestore DB_PANTHER;
    //Map<String, Object> DOCTOLOAD;
    String TAG;

    Spinner SpinCategories;
    RecyclerView RV_ProductsList;
    TextView TxtvCountProducts;
    Button BtnShoppingCart;

    List<VGProduct> ProductsList;
    List<VGProduct> FilteredProductsList;
    List<VGProduct> ShoppingCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_purchase);

        DB_PANTHER = FirebaseFirestore.getInstance();
        TxtvCountProducts = (TextView) findViewById(R.id.txtvCountProducts);
        BtnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);

        SpinCategories = (Spinner) findViewById(R.id.spin_categories);
        REFRESH_CATEGORIES_LIST(SpinCategories);
        SpinCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                RefreshFilteredProductslist();
                StartProductsAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RV_ProductsList = (RecyclerView) findViewById(R.id.recyclervList);
        RV_ProductsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Context);
        RV_ProductsList.setLayoutManager(layoutManager);

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
        ProductsList = new ArrayList<>();
        FilteredProductsList = new ArrayList<>();

        DB_PANTHER.collection("GAMES").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    int i = 0;
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        Log.d(TAG, DOC.getId() + " => " + DOC.getData());
                        ProductsList.add(new VGProduct(
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
                            ProductsList.size() + " Products",Toast.LENGTH_SHORT).show();
                }
            });
        FilteredProductsList = ProductsList;
        ShoppingCartList = new ArrayList<>();
    }

    private void RefreshFilteredProductslist()
    {
        FilteredProductsList = new ArrayList<>();
        String ValueToQuery = SpinCategories.getSelectedItem().toString();
        if(SpinCategories.getSelectedItemPosition() == 0) FilteredProductsList = ProductsList;
        else
        {
            for (VGProduct product : ProductsList)
            {
                if (product.getCategory().equals(ValueToQuery)) FilteredProductsList.add(product);
            }
        }

        /*
        String FieldToQuery = getString(R.string.DB_Categ);
        DB_PANTHER.collection("GAMES")
            .whereEqualTo(FieldToQuery, ValueToQuery).get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
            {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                {
                    int i = 0;
                    for (QueryDocumentSnapshot DOC : queryDocumentSnapshots)
                    {
                        Log.d(TAG, DOC.getId() + " => " + DOC.getData());
                        FilteredProductsList.add(new VGProduct(
                                DOC.get("IDGAME", Integer.class),
                                DOC.get("CATEGORYID", Integer.class),
                                DOC.get("YEAR", Integer.class),
                                DOC.get("PRICE", Integer.class),
                                DOC.get("INVENTARY", Integer.class),
                                DOC.getString(getString(R.string.DB_Games_Title)),
                                DOC.getString(getString(R.string.DB_Games_Descr)),
                                DOC.getString("CODIMAGE"),
                                DOC.getString(getString(R.string.DB_Categ)),
                                i, false));
                        i++;
                    }
                    Toast.makeText(getApplicationContext(),
                            ProductsList.size() + " Products",Toast.LENGTH_SHORT).show();
                }
            });
        */
    }

    private void StartProductsAdapter()
    {
        ProductCardAdapter Adapter = new ProductCardAdapter(TxtvCountProducts, BtnShoppingCart,
                ProductsList, FilteredProductsList, ShoppingCartList);
        RV_ProductsList.setAdapter(Adapter);
    }

    public void LaunchShoppingCarActivity(View view)
    {
        //Intent IntentProductDetails = new Intent(this, ProductDetailsActivity.class);
        //IntentProductDetails.putExtra("Product", Products.get(SelectedProductPosition).Product);
        //startActivity(IntentProductDetails);
    }

}