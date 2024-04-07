package com.example.w24_3175_g12_one_clickeat.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.model.FavShop;

import java.util.List;

public class FavItemAdapter extends BaseAdapter {
    List<FavShop>  favShopList;

    public FavItemAdapter(List<FavShop> favShopList) {
        this.favShopList = favShopList;
    }

    @Override
    public int getCount() {
        return favShopList.size();
    }

    @Override
    public Object getItem(int position) {
        return favShopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shop_item, parent, false);
        }
        TextView shopName = convertView.findViewById(R.id.shop_name);
        TextView deliveryTime = convertView.findViewById(R.id.shop_deliver_time);
        TextView rating = convertView.findViewById(R.id.shop_ratings);
        shopName.setText(favShopList.get(position).getName());
        deliveryTime.setText(favShopList.get(position).getDeliveryTime());
        rating.setText(String.valueOf(favShopList.get(position).getRating()));
        ImageView imageView = convertView.findViewById(R.id.shop_picture);
        imageView.setImageResource(favShopList.get(position).getImageResource());
        return convertView;

    }
}
