package com.example.w24_3175_g12_one_clickeat.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItems(Item... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertItemsFromList(List<Item> itemList);

    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    @Query("DELETE FROM items")
    void deleteAllItems();


    @Query("SELECT * FROM items where shopid = :shopid")
    List<Item> getItemsByShopId(long shopid);
}
