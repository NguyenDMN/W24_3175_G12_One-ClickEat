package com.example.w24_3175_g12_one_clickeat.databases;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.w24_3175_g12_one_clickeat.interfaces.FavshopDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.ItemDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.OrderDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.OrderHistoryDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.ShopDao;
import com.example.w24_3175_g12_one_clickeat.interfaces.UserDao;
import com.example.w24_3175_g12_one_clickeat.model.FavShop;
import com.example.w24_3175_g12_one_clickeat.model.Item;
import com.example.w24_3175_g12_one_clickeat.model.Order;
import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
import com.example.w24_3175_g12_one_clickeat.model.User;


@Database(entities = {User.class, Shop.class, Item.class, Order.class, OrderHistory.class, FavShop.class}, version = 17, exportSchema = false)

public abstract class OneClickEatDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ShopDao shopDao();

    public abstract ItemDao itemDao();
    //public abstract User

    public abstract OrderDao orderDao();

    public abstract OrderHistoryDao orderHistoryDao();

    public abstract FavshopDao favshopDao();


}
