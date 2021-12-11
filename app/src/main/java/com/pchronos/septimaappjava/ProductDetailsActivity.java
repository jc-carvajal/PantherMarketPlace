package com.pchronos.septimaappjava;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity
{
    FirebaseFirestore DB_PANTHER;
    Map<String, Object> DOCTOLOAD;
    Spinner SpinStates;
    ListView Lstv1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        DB_PANTHER = FirebaseFirestore.getInstance();
        Lstv1 = (ListView) findViewById(R.id.lstv1);

        SpinStates =(Spinner) findViewById(R.id.spin_states);
        REFRESH_STATES_SPIN(SpinStates);
        SpinStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                REFRESH_TOWNS_LIST(view);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
    }

    private void REFRESH_STATES_SPIN(View view)
    {
        List<String> ARRAYTOLIST = new ArrayList<String>();
        DB_PANTHER.collection("STATES").orderBy("STATE").get()
            .addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {

                }
            })
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
            });
    }

    public void REFRESH_TOWNS_LIST(View view)
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
                        ARRAYTOLIST.add(DOC.get("IDTOWN") + ": " +  DOC.get("TOWN"));
                    }
                    String DATATOLIST[] = ARRAYTOLIST.toArray(new String[ARRAYTOLIST.size()]);
                    ArrayAdapter<String> ADAPTER = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_list_item_1, DATATOLIST);
                    Lstv1.setAdapter(ADAPTER);
                    Toast.makeText(view.getContext(), ARRAYTOLIST.size() + " Towns", Toast.LENGTH_LONG).show();
                }
            });
    }

}