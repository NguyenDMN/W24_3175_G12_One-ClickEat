package com.example.w24_3175_g12_one_clickeat.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favshops")
public class FavShop {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name="email")
    private String email;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "rating")
    private double rating;

    @NonNull
    @ColumnInfo(name = "deliverytime")
    private String deliveryTime;

    @NonNull
    @ColumnInfo(name = "imageresource")
    private int imageResource;


    public FavShop(long id, @NonNull String email, @NonNull String name, double rating, @NonNull String deliveryTime, int imageResource) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageResource = imageResource;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @NonNull
    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(@NonNull String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
