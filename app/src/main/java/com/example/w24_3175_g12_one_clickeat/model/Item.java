package com.example.w24_3175_g12_one_clickeat.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
@Entity(tableName = "items")
public class Item {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "price")
    private double price;

    @NonNull
    @ColumnInfo(name = "shopId")
    private long shopId;

    @NonNull
    @ColumnInfo(name = "imageresource")
    private int imageResource;

    public long getId() {
        return id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item(@NonNull String name, @NonNull String description, double price, long shopId, int imageResource) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.shopId = shopId;
        this.imageResource = imageResource;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

//    private
}
