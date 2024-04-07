package com.example.w24_3175_g12_one_clickeat.adpater;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.room.Room;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.interfaces.OnItemRemovedListener;
import com.example.w24_3175_g12_one_clickeat.model.Order;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CartItemAdapter extends BaseAdapter {


    OneClickEatDatabase ocdb;
    List<Order> orderList;
    private Context context;
    int selectIndex = -1;
    private Handler mainHandler;

    private OnItemRemovedListener mlistener;

    public void setOnItemRemoveListener(OnItemRemovedListener listener) {
        mlistener = listener;
    }

    public CartItemAdapter(OneClickEatDatabase ocdb, List<Order> orderList, Context context) {
        this.ocdb = ocdb;
        this.orderList = orderList;
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
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

        Button removeBtn = convertView.findViewById(R.id.removeBtn);
        EditText foodQuantity = convertView.findViewById(R.id.editTextQuantityNum);

        foodName.setText(orderList.get(position).getItemName());

        double foodPricePerUnit =orderList.get(position).getPrice();
        long quantityNum = orderList.get(position).getQuantity();

        double totalPricefinal = (quantityNum * foodPricePerUnit);

        DecimalFormat df = new DecimalFormat("$#.##");
        String finalTotalPriceFormat = df.format(totalPricefinal);

        totalPriceDisplay.setText(finalTotalPriceFormat);
        foodQuantity.setText(String.valueOf(quantityNum));

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Long orderId = orderList.get(position).getId();
                double foodPricePerUnit =orderList.get(position).getPrice();
                long quantityNum = orderList.get(position).getQuantity();

                double totalPricefinal = (quantityNum * foodPricePerUnit);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ocdb.orderDao().deleteOrderFromId(orderId);
                        orderList.remove(position);

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                                if (mlistener != null) {
                                    mlistener.onItemRemoved(orderList);
                                } else {
                                    Log.d("ISNULL", "mlistner is null");
                                }
                            }
                        });
                    }
                }).start();
            }
        });



        return convertView;
    }
}
