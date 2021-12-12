package com.pchronos.septimaappjava;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductViewHolder>
{
    TextView TxtvCountProducts;
    Button BtnShoppingCar;
    List<VGProduct> ProductsList;
    List<VGProduct> FilteredProductsList;
    List<VGProduct> ShoppingCarList;
    String ImageURL="";

    public ProductCardAdapter(TextView txtvCountProducts, Button btnShoppingCar,
                              List<VGProduct> productsList, List<VGProduct> filteredProductsList,
                              List<VGProduct> shoppingCarList)
    {
        TxtvCountProducts = txtvCountProducts;
        BtnShoppingCar = btnShoppingCar;
        ProductsList = productsList;
        FilteredProductsList = filteredProductsList;
        ShoppingCarList = shoppingCarList;
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
        holder.ProductName.setText(FilteredProductsList.get(position).getProduct());
        holder.ProductDescription.setText(FilteredProductsList.get(position).getDescription());
        holder.InShoppingCar.setChecked(FilteredProductsList.get(position).isInShoppingCar());

        //holder.ProductImage.setImageResource(R.drawable.ic_launcher_background);
        //holder.ProductImage.setImageResource(FilteredProductsList.get(position).getImageCode);
        ImageURL = FilteredProductsList.get(position).getImageCode();
        if (ImageURL.isEmpty())
        {
            holder.ProductImage.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            Glide.with(holder.itemView).load(ImageURL).into(holder.ProductImage);
        }

        holder.InShoppingCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = holder.getAdapterPosition();
                int j = FilteredProductsList.get(i).getPosition();
                boolean Added = ProductsList.get(j).isInShoppingCar();
                if (!Added && holder.InShoppingCar.isChecked())
                {
                    ProductsList.get(j).setInShoppingCar(true);
                    ShoppingCarList.add(ProductsList.get(j));
                }
                else if (Added && !holder.InShoppingCar.isChecked())
                {
                    ProductsList.get(j).setInShoppingCar(false);
                    ShoppingCarList.remove(ProductsList.get(j));
                }
                TxtvCountProducts.setText(""+ShoppingCarList.size());
            }
        });

        holder.ProductCardVIew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int i = holder.getAdapterPosition();
                Intent IntentProductDetails = new Intent(v.getContext(), ProductDetailsActivity.class);

                IntentProductDetails.putExtra("ProductId", ProductsList.get(i).getIdProduct());
                IntentProductDetails.putExtra("CategoryId", ProductsList.get(i).getCategoryId());
                IntentProductDetails.putExtra("Year", ProductsList.get(i).getYear());
                IntentProductDetails.putExtra("Price", ProductsList.get(i).getPrice());
                IntentProductDetails.putExtra("Inventary", ProductsList.get(i).getInventary());
                IntentProductDetails.putExtra("Product", ProductsList.get(i).getProduct());
                IntentProductDetails.putExtra("Description", ProductsList.get(i).getDescription());
                IntentProductDetails.putExtra("ImageCode", ProductsList.get(i).getImageCode());

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

        BtnShoppingCar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Launch ShoppingCardActivity
                Toast.makeText(v.getContext(), "View Shopping Car", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() { return FilteredProductsList.size(); }

    public class ProductViewHolder extends RecyclerView.ViewHolder
    {
        CardView ProductCardVIew;
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductDescription;
        CheckBox InShoppingCar;

        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ProductCardVIew = (CardView) itemView.findViewById(R.id.product_card);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgCard);
            ProductName = (TextView) itemView.findViewById(R.id.txtvCard1);
            ProductDescription = (TextView) itemView.findViewById(R.id.txtvCard2);
            InShoppingCar = (CheckBox) itemView.findViewById(R.id.cbShoppingCar);
        }
    }

}
