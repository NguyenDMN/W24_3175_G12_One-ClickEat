package com.example.w24_3175_g12_one_clickeat.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.w24_3175_g12_one_clickeat.interfaces.UserDao;
import com.example.w24_3175_g12_one_clickeat.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class OneClickEatDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    //public abstract User
}
