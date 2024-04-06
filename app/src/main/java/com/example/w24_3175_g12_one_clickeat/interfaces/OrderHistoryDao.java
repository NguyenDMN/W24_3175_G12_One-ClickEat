package com.example.w24_3175_g12_one_clickeat.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;

import java.util.*;

@Dao
public interface OrderHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrderHistory(OrderHistory... orderHistories);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    Long[] insertOrderHistoriesFromList(List<OrderHistory> orderHistoryList);

    @Query("SELECT * FROM orderhistories")
    List<OrderHistory> getAllOrderHistories();

    @Query("SELECT * FROM orderhistories where email = :email order by createdDate DESC")
    List<OrderHistory> getAllOrderHistoriesByEmail(String email);

    @Query("DELETE FROM orderhistories")
    void deleteAllShops();
}
