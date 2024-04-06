package com.example.w24_3175_g12_one_clickeat.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;
import java.util.*;

public class HistoryAdapter extends BaseAdapter {

    List<OrderHistory> orderHistoryList;

    int selectIndex = -1;

    public HistoryAdapter(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    public List<OrderHistory> getOrderHistoryList() {
        return orderHistoryList;
    }

    public void setOrderHistoryList(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
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
        return orderHistoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderHistoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_history_item_adapter, viewGroup, false);

        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DecimalFormat df = new DecimalFormat("$#.##");
        String finalTotalPriceFormat = df.format(orderHistoryList.get(i).getPrice());
        TextView orderId = view.findViewById(R.id.history_order_id);
        orderId.setText(orderHistoryList.get(i).getId().toString());
        TextView price = view.findViewById(R.id.history_price);
        price.setText("$" + finalTotalPriceFormat);
        TextView orderDate = view.findViewById(R.id.history_date);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        orderDate.setText(sdf.format(orderHistoryList.get(i).getCreatedDate()));
        return view;
    }
}
