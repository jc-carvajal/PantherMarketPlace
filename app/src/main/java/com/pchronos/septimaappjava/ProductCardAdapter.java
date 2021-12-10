package com.pchronos.septimaappjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductViewHolder>
{
    public class ProductViewHolder extends RecyclerView.ViewHolder
    {
        CardView ProductCardVIew;
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductDescription;

        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ProductCardVIew = (CardView) itemView.findViewById(R.id.product_card);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgCard);
            ProductName = (TextView) itemView.findViewById(R.id.txtvCard1);
            ProductDescription = (TextView) itemView.findViewById(R.id.txtvCard2);
        }
    }

    List<VGProduct> Products;
    String ImageURL="";
    //private ClickListener xxx;

    public ProductCardAdapter(List<VGProduct> products)
    {
        Products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view,
                parent, false);
        ProductViewHolder PVH = new ProductViewHolder(view);
        return PVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position)
    {
        holder.ProductName.setText(Products.get(position).Product);
        holder.ProductDescription.setText(Products.get(position).Description);
        ImageURL = Products.get(position).ImageCode;
        //holder.ProductImage.setImageResource(Products.get(position).ImageCode);
        if (ImageURL.isEmpty())
        {
            holder.ProductImage.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            Glide.with(holder.itemView).load(ImageURL).into(holder.ProductImage);
        }
    }

    @Override
    public int getItemCount()
    {
        // return 0;
        return Products.size();
    }
    /*
    public class ClickListener(int position, View view)
    {

    }
    */
}
