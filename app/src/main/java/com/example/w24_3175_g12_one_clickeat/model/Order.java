package com.example.w24_3175_g12_one_clickeat.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name="email")
    private String email;



    @NonNull
    @ColumnInfo(name="itemId")
    private long itemId;

    @NonNull
    @ColumnInfo(name = "itemName")
    private String itemName;



    @NonNull
    @ColumnInfo(name="quantity")
    private long quantity;

    @NonNull
    @ColumnInfo(name="price")
    private double price;

    public Order(@NonNull String email, long itemId, String itemName ,long quantity, double price) {
        this.email = email;
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.price = price;
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
