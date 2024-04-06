package com.example.w24_3175_g12_one_clickeat.interfaces;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrders(Order... orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertOrdersFromList(List<Order> orderList);

    @Query("SELECT * FROM orders")
    List<Order> getAllOrders();

    @Query("SELECT * FROM orders WHERE itemId = :itemId AND email = :userEmail LIMIT 1")
    Order getOrderByItemId(long itemId, String userEmail);

    @Query("UPDATE orders SET quantity = :newQuantity WHERE itemId = :itemId AND email = :userEmail")
    void updateOrder(long itemId, String userEmail, long newQuantity);






}
