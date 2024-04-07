package com.example.w24_3175_g12_one_clickeat.model;

import android.net.Uri;

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
    @ColumnInfo(name="firstname")
    private String firstName;
    @NonNull
    @ColumnInfo(name="lastname")
    private String lastName;
    @NonNull
    @ColumnInfo(name="password")
    private String Password;


    @ColumnInfo(name="imageuri")
    private String imageUri;


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public User() {

    }

    public User(@NonNull String email, @NonNull String password, @NonNull String firstName, @NonNull String lastName) {
        Email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        Password = password;
    }


    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
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
