package com.pchronos.septimaappjava;

public class VGProduct
{
    private int IdProduct;
    private int CategoryId;
    private int Year;
    private int Price;
    private int Inventary;
    private String Product;
    private String Description;
    private String ImageCode;
    private String Category;
    private int Position;
    private boolean InShoppingCar;

    public VGProduct()
    {

    }

    public VGProduct(int idProduct, int categoryId, int year, int price, int inventary,
                     String product, String description, String imageCode,
                     String category, int position, boolean inShoppingCar)
    {
        IdProduct = idProduct;
        CategoryId = categoryId;
        Year = year;
        Price = price;
        Inventary = inventary;
        Product = product;
        Description = description;
        ImageCode = imageCode;
        Category = category;
        Position = position;
        InShoppingCar = inShoppingCar;
    }

    public int getIdProduct() { return IdProduct; }

    public void setIdProduct(int idProduct) { IdProduct = idProduct; }

    public int getCategoryId() { return CategoryId; }

    public void setCategoryId(int categoryId) { CategoryId = categoryId; }

    public int getYear() { return Year; }

    public void setYear(int year) { Year = year; }

    public int getPrice() { return Price; }

    public void setPrice(int price) { Price = price; }

    public int getInventary() { return Inventary; }

    public void setInventary(int inventary) { Inventary = inventary; }

    public String getProduct() { return Product; }

    public void setProduct(String product) { Product = product; }

    public String getDescription() { return Description; }

    public void setDescription(String description) { Description = description; }

    public String getImageCode() { return ImageCode; }

    public void setImageCode(String imageCode) { ImageCode = imageCode; }

    public String getCategory() { return Category; }

    public void setCategory(String category) { Category = category; }

    public int getPosition() { return Position; }

    public void setPosition(int position) { Position = position; }

    public boolean isInShoppingCar() { return InShoppingCar; }

    public void setInShoppingCar(boolean inShoppingCar) { InShoppingCar = inShoppingCar; }

}
