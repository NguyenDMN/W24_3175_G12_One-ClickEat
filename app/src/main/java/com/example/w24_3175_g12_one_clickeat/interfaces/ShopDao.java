package com.example.w24_3175_g12_one_clickeat.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.Shop;
import com.example.w24_3175_g12_one_clickeat.model.User;

import java.util.List;

@Dao
public interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertShops(Shop... shops);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertShopsFromList(List<Shop> shopList);

    @Query("SELECT * FROM shops")
    List<Shop> getAllShops();

    @Query("DELETE FROM shops")
    void deleteAllShops();

    @Query("SELECT * FROM shops where id = :id")
    Shop getOneShopById(String id);


}
