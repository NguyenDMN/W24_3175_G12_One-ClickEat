package com.example.w24_3175_g12_one_clickeat.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Order;

import java.text.DecimalFormat;
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

        TextView foodName = convertView.findViewById(R.id.txtViewItemName);

        TextView totalPriceDisplay = convertView.findViewById(R.id.txtViewItemPrice);

        TextView removeTextView = convertView.findViewById(R.id.TxtViewCartItemRemove);

        EditText foodQuantity = convertView.findViewById(R.id.editTextQuantityNum);

        foodName.setText(orderList.get(position).getItemName());

        double foodPricePerUnit =orderList.get(position).getPrice();
        long quantityNum = orderList.get(position).getQuantity();

        double totalPricefinal = (quantityNum * foodPricePerUnit);

        DecimalFormat df = new DecimalFormat("$#.##");
        String finalTotalPriceFormat = df.format(totalPricefinal);

        totalPriceDisplay.setText(finalTotalPriceFormat);
        foodQuantity.setText(String.valueOf(quantityNum));

        return convertView;
    }
}
