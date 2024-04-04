package com.example.w24_3175_g12_one_clickeat.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.w24_3175_g12_one_clickeat.interfaces.ShopDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.UserDao;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
import com.example.w24_3175_g12_one_clickeat.model.User;

@Database(entities = {User.class, Shop.class}, version = 2, exportSchema = false)
public abstract class OneClickEatDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ShopDao shopDao();
    //public abstract User
}
