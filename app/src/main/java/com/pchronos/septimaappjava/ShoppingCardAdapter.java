package com.pchronos.septimaappjava;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingCardAdapter extends RecyclerView.Adapter<ShoppingCardAdapter.ProductViewHolder>
{
    TextView TxtvCountProducts;
    Button BtnBuy;
    //List<VGProduct> ProductsList;
    //List<VGProduct> FilteredProductsList;
    List<VGProduct> ShoppingCartList;
    String ImageURL="";

    public ShoppingCardAdapter(TextView txtvCountProducts, Button btnBuy,
                               List<VGProduct> shoppingCartList)
    {
        TxtvCountProducts = txtvCountProducts;
        BtnBuy = btnBuy;
        ShoppingCartList = shoppingCartList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_card_view,
                parent, false);
        ProductViewHolder PVH = new ProductViewHolder(view);
        return PVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position)
    {
        holder.ProductName.setText(ShoppingCartList.get(position).getProduct());
        holder.ProductDescription.setText(ShoppingCartList.get(position).getDescription());
        holder.ProductPrice.setText(ShoppingCartList.get(position).getPrice());

        //holder.ProductImage.setImageResource(R.drawable.ic_launcher_background);
        //holder.ProductImage.setImageResource(FilteredProductsList.get(position).getImageCode);
        ImageURL = ShoppingCartList.get(position).getImageCode();
        if (ImageURL.isEmpty())
        {
            holder.ProductImage.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            Glide.with(holder.itemView).load(ImageURL).into(holder.ProductImage);
        }

        holder.ProductCardVIew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int i = holder.getAdapterPosition();
                Intent IntentProductDetails = new Intent(v.getContext(), ProductDetailsActivity.class);

                IntentProductDetails.putExtra("ProductId", ShoppingCartList.get(i).getIdProduct());
                IntentProductDetails.putExtra("CategoryId", ShoppingCartList.get(i).getCategoryId());
                IntentProductDetails.putExtra("Year", ShoppingCartList.get(i).getYear());
                IntentProductDetails.putExtra("Price", ShoppingCartList.get(i).getPrice());
                IntentProductDetails.putExtra("Inventary", ShoppingCartList.get(i).getInventary());
                IntentProductDetails.putExtra("Product", ShoppingCartList.get(i).getProduct());
                IntentProductDetails.putExtra("Description", ShoppingCartList.get(i).getDescription());
                IntentProductDetails.putExtra("ImageCode", ShoppingCartList.get(i).getImageCode());

                startActivity(v.getContext(), IntentProductDetails, Bundle.EMPTY);
            }
        });
        /*
        holder.ProductImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent IntentProductDetails = new Intent(v.getContext(), ProductDetailsActivity.class);
                startActivity(v.getContext(), IntentProductDetails, Bundle.EMPTY);
            }
        });
        */

        BtnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), "Process Order", Toast.LENGTH_SHORT).show();
                // Intent IntentShoppingCart = new Intent(v.getContext(), ShoppingCartActivity.class);
                // IntentShoppingCart.putExtra("ImageCode", ProductsList.get(i).getImageCode());
                // startActivity(v.getContext(), IntentShoppingCart, Bundle.EMPTY);
            }
        });

    }

    @Override
    public int getItemCount() { return ShoppingCartList.size(); }

    public class ProductViewHolder extends RecyclerView.ViewHolder
    {
        CardView ProductCardVIew;
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductDescription;
        TextView ProductPrice;

        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ProductCardVIew = (CardView) itemView.findViewById(R.id.product_card);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgCard);
            ProductName = (TextView) itemView.findViewById(R.id.txtvCard1);
            ProductDescription = (TextView) itemView.findViewById(R.id.txtvCard2);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtvCard3);
        }
    }

}
