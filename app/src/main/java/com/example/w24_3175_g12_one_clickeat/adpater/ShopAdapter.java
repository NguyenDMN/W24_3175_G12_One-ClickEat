package com.example.w24_3175_g12_one_clickeat.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.model.Shop;

import java.util.List;

public class ShopAdapter extends BaseAdapter {

    List<Shop> shopList;
    int selectedIndex = -1;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
        notifyDataSetChanged();
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public ShopAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int i) {
        return shopList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shop_item, viewGroup, false);
        }
        TextView shopName = view.findViewById(R.id.shop_name);
        TextView deliveryTime = view.findViewById(R.id.shop_deliver_time);
        TextView rating = view.findViewById(R.id.shop_ratings);
        shopName.setText(shopList.get(i).getName());
        deliveryTime.setText(shopList.get(i).getDeliveryTime());
        rating.setText(String.valueOf(shopList.get(i).getRating()));
        ImageView imageView = view.findViewById(R.id.shop_picture);
        imageView.setImageResource(shopList.get(i).getImageResource());
        return view;


    }
}
