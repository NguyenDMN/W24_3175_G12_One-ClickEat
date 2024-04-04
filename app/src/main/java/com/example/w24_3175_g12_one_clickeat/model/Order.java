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
    private String id;

    @NonNull
    @ColumnInfo(name="email")
    private String email;



}
