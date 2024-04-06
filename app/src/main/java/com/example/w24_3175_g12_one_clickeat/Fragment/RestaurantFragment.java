package com.example.w24_3175_g12_one_clickeat.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.adpater.FoodItemAdapter;
import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.FavShop;
import com.example.w24_3175_g12_one_clickeat.model.Item;
import com.example.w24_3175_g12_one_clickeat.model.Order;
import com.example.w24_3175_g12_one_clickeat.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {

    OneClickEatDatabase ocdb;
    FoodItemAdapter foodItemAdapter;
    List<Order> orderList = new ArrayList<>();
    long shopId = -1;
    List<Item> menuItemList = new ArrayList<>();
    ListView resView;
    TextView txtViewResName;
    String email;
    Shop shopInfor;

    List<FavShop> favShopList;

    ImageView imgHeartFav;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email", "NoEmail");
            shopId = getArguments().getLong("shopId", -1);
        }
        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                favShopList = ocdb.favshopDao().getAllFavShops(email);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        txtViewResName = view.findViewById(R.id.txtViewResName);
        resView = view.findViewById(R.id.ResView);
        imgHeartFav = view.findViewById(R.id.imgViewHeartFav);



        if (favShopList != null && favShopList.contains(shopId)) {
            imgHeartFav.setImageResource(R.drawable.heart_filled);
            imgHeartFav.setTag(R.drawable.heart_filled);
        } else {
            imgHeartFav.setImageResource(R.drawable.heart_unfilled);
            imgHeartFav.setTag(R.drawable.heart_unfilled);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                menuItemList = ocdb.itemDao().getItemsByShopId(shopId);
                shopInfor = ocdb.shopDao().getOneShopById(shopId);
                String shopName = shopInfor.getName();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewResName.setText("Welcome to " + shopName);
                        foodItemAdapter = new FoodItemAdapter(menuItemList);
                        resView.setAdapter(foodItemAdapter);
                    }
                });
            }
        });


        resView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    long itemId = menuItemList.get(position).getId();
                    String itemName = menuItemList.get(position).getName();
                    double itemPrice = menuItemList.get(position).getPrice();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Check if the order already exists for the item
                            Order existingOrder = ocdb.orderDao().getOrderByItemId(itemId, email);
                            if (existingOrder != null) {
                                // If an order already exists, increase the quantity by 1
                                existingOrder.setQuantity(existingOrder.getQuantity() + 1);
                                long newQuantity = existingOrder.getQuantity();
                                ocdb.orderDao().updateOrder(itemId,email,newQuantity);
                            } else {
                                // If no order exists, insert a new order with quantity 1
                                ocdb.orderDao().insertOrders(new Order(email, itemId, itemName,1,itemPrice));
                                // ocdb.orderDao().insertOrders(new Order(email, itemId, 1));
                            }
                        }
                    });
                }
            }
        });





        imgHeartFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current drawable resource ID of the ImageView
                int currentDrawable = (Integer) imgHeartFav.getTag();

                // Check the current drawable and toggle it
                if (currentDrawable == R.drawable.heart_unfilled) {
                    imgHeartFav.setImageResource(R.drawable.heart_filled);
                    // Update the tag to reflect the new drawable resource ID
                    imgHeartFav.setTag(R.drawable.heart_filled);

                    FavShop newFavShop = new FavShop(shopInfor.getId(),email,shopInfor.getName()
                            ,shopInfor.getRating(),shopInfor.getDeliveryTime(), shopInfor.getImageResource());


                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            ocdb.favshopDao().insertFavShops(newFavShop);
                        }
                    });
                } else {
                    imgHeartFav.setImageResource(R.drawable.heart_unfilled);
                    // Update the tag to reflect the new drawable resource ID
                    imgHeartFav.setTag(R.drawable.heart_unfilled);
                    Log.d("SHOPNAME", shopInfor.getName());

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            ocdb.favshopDao().deleteOneFavShop(shopInfor.getId());
                        }
                    });
                }
            }
        });


    }
}
