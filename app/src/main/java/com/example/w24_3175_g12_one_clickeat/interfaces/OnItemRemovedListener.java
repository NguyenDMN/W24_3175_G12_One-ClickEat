package com.example.w24_3175_g12_one_clickeat.interfaces;

import com.example.w24_3175_g12_one_clickeat.model.Order;

import java.util.List;

public interface OnItemRemovedListener {
    void onItemRemoved(List<Order> orderList);
}

