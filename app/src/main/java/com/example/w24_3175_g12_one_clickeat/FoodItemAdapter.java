package com.example.w24_3175_g12_one_clickeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.model.Item;

import java.util.List;

public class FoodItemAdapter extends BaseAdapter {

    List<Item> itemList;

    int selectIndex = -1;

    public FoodItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void updateData(List<Item> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        return null;
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_item, viewGroup, false);

        }
        TextView foodName = view.findViewById(R.id.item_name);
        TextView foodDescription = view.findViewById(R.id.item_description);
        TextView foodPrice = view.findViewById(R.id.item_price);
        ImageView foodImg = view.findViewById(R.id.item_icon);
        foodName.setText(itemList.get(i).getName());
        foodDescription.setText(itemList.get(i).getDescription());
        foodPrice.setText(String.valueOf(itemList.get(i).getPrice()));
        foodImg.setImageResource(itemList.get(i).getImageResource());
        return view;

    }
}
