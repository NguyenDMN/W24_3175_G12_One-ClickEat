package com.example.w24_3175_g12_one_clickeat.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shops")
public class Shop {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "rating")
    private double rating;

    @NonNull
    @ColumnInfo(name = "deliverytime")
    private String deliveryTime;

    @NonNull
    @ColumnInfo(name = "imageresource")
    private int imageResource;

    @NonNull
    @ColumnInfo(name="latitude")
    private double latitude;

    @NonNull
    @ColumnInfo(name="longtitude")
    private double longtitude;


    public Shop( @NonNull String name, @NonNull String description, double rating, @NonNull String deliveryTime, int imageResource, double latitude, double longtitude) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageResource = imageResource;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Shop() {}



    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
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


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
