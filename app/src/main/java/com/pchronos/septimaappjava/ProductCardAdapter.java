package com.pchronos.septimaappjava;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductViewHolder>
{
    Context context2;
    TextView TxtvCountProducts;
    Button BtnShoppingCart;
    List<VGProduct> ProductsList;
    List<VGProduct> FilteredProductsList;
    List<VGProduct> ShoppingCartList;
    String ImageURL="";

    public ProductCardAdapter(Context context2, TextView txtvCountProducts, Button btnShoppingCart,
                              List<VGProduct> productsList, List<VGProduct> filteredProductsList,
                              List<VGProduct> shoppingCartList)
    {
        this.context2 = context2;
        TxtvCountProducts = txtvCountProducts;
        BtnShoppingCart = btnShoppingCart;
        ProductsList = productsList;
        FilteredProductsList = filteredProductsList;
        ShoppingCartList = shoppingCartList;
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
        holder.InShoppingCart.setChecked(FilteredProductsList.get(position).isInShoppingCart());

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

        holder.InShoppingCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = holder.getAdapterPosition();
                int j = FilteredProductsList.get(i).getPosition();
                boolean Added = ProductsList.get(j).isInShoppingCart();
                if (!Added && holder.InShoppingCart.isChecked())
                {
                    ProductsList.get(j).setInShoppingCart(true);
                    ShoppingCartList.add(ProductsList.get(j));
                }
                else if (Added && !holder.InShoppingCart.isChecked())
                {
                    ProductsList.get(j).setInShoppingCart(false);
                    ShoppingCartList.remove(ProductsList.get(j));
                }
                TxtvCountProducts.setText(""+ShoppingCartList.size());
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

        BtnShoppingCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Toast.makeText(v.getContext(), "View Shopping Cart", Toast.LENGTH_SHORT).show();
                Intent IntentShoppingCart = new Intent(context2, ShoppingCartActivity.class);
                // Problemas para pasar ShoppingCartList a ShoppingCartActivity
                // IntentShoppingCart.putExtra("ShoppingCart", (Serializable) ShoppingCartList);
                context2.startActivity(IntentShoppingCart);
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
        CheckBox InShoppingCart;

        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ProductCardVIew = (CardView) itemView.findViewById(R.id.product_card);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgCard);
            ProductName = (TextView) itemView.findViewById(R.id.txtvCard1);
            ProductDescription = (TextView) itemView.findViewById(R.id.txtvCard2);
            InShoppingCart = (CheckBox) itemView.findViewById(R.id.cbShoppingCart);
        }
    }

}
