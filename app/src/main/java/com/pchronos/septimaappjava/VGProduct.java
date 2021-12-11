package com.pchronos.septimaappjava;

public class VGProduct
{
    int IdProduct, CategoryId, Year, Price, Inventary;
    String Product, Description, ImageCode;

    public VGProduct()
    {

    }

    public VGProduct(int idProduct, int categoryId, int year, int price, int inventary,
                   String product, String description, String imageCode)
    {
        IdProduct = idProduct;
        CategoryId = categoryId;
        Year = year;
        Price = price;
        Inventary = inventary;
        Product = product;
        Description = description;
        ImageCode = imageCode;
    }

}
