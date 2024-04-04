package com.example.w24_3175_g12_one_clickeat.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="email")
    private String Email;
    @NonNull
    @ColumnInfo(name="password")
    private String Password;

    public User() {

    }
    public User(@NonNull String email, @NonNull String password) {
        Email = email;
        Password = password;
    }

    @NonNull
    public String getEmail() {
        return Email;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    @NonNull
    public String getPassword() {
        return Password;
    }

    public void setPassword(@NonNull String password) {
        Password = password;
    }
}
