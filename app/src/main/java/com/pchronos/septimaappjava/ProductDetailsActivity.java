package com.pchronos.septimaappjava;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity
{
    //FirebaseFirestore DB_PANTHER;
    String ImageURL;
    ImageView ProductImage;
    TextView IdProduct;
    TextView CategoryId;
    TextView Year;
    TextView Price;
    TextView Inventary;
    TextView Product;
    TextView Description;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //DB_PANTHER = FirebaseFirestore.getInstance();
        ProductImage = (ImageView) findViewById(R.id.img1);
        Product = (TextView) findViewById(R.id.txtv1);
        Year = (TextView) findViewById(R.id.txtv2);
        Price = (TextView) findViewById(R.id.txtv3);
        Description = (TextView) findViewById(R.id.txtv4);
        IdProduct = (TextView) findViewById(R.id.txtv5);
        CategoryId = (TextView) findViewById(R.id.txtv6);
        Inventary = (TextView) findViewById(R.id.txtv7);

        Bundle data = getIntent().getExtras();
        ImageURL = data.getString("ImageCode");
        IdProduct.setText("" + data.get("ProductId"));
        CategoryId.setText("" + data.get("CategoryId"));
        Year.setText("Año: " + data.get("Year"));
        Price.setText("Precio: $" + data.get("Price"));
        Inventary.setText("" + data.get("Inventary"));
        Product.setText("" + data.getString("Product"));
        Description.setText("" + data.getString("Description"));

        if (ImageURL.isEmpty())
        {
            ProductImage.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            Glide.with(this).load(ImageURL).into(ProductImage);
        }
    }

}