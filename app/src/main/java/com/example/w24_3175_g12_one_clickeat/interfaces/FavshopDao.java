package com.example.w24_3175_g12_one_clickeat.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.FavShop;

import java.util.List;


@Dao
public interface FavshopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavShops(FavShop... favShops);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertFavShopsFromList(List<FavShop> favShopListhopList);


    @Query("SELECT * FROM favshops WHERE email =:email")
    List<FavShop> getAllFavShops(String email);

    @Query("DELETE FROM favshops WHERE id=:shopid")
    void deleteOneFavShop(long shopid);






}
