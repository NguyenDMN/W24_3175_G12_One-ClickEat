package com.example.w24_3175_g12_one_clickeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Order;

import java.util.List;

public class CartItemAdapter extends BaseAdapter {


    List<Order> orderList;

    int selectIndex = -1;

    public CartItemAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;

        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_adapter,parent,false);
        }

        TextView txtViewItemName = convertView.findViewById(R.id.txtViewItemName);

        TextView txtViewItemPrice = convertView.findViewById(R.id.txtViewItemPrice);

        Button txtViewItemRemove = convertView.findViewById(R.id.removeBtn);

        EditText editTxtItemQuantity = convertView.findViewById(R.id.editTextQuantityNum);





        return null;
    }
}
