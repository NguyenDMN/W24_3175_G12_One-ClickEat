package com.example.w24_3175_g12_one_clickeat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.adpater.FavItemAdapter;
import com.example.w24_3175_g12_one_clickeat.adpater.ShopAdapter;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.FavShop;
import com.example.w24_3175_g12_one_clickeat.model.Shop;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {


    String email;
    OneClickEatDatabase ocdb;
    List<FavShop> favList;
    ListView favListView;

    FavItemAdapter favItemAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            email = getArguments().getString("email");

        }






    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favListView = view.findViewById(R.id.listViewFav);

        // Move the database initialization and access code inside the ExecutorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
                favList = ocdb.favshopDao().getAllFavShops(email);

                // Update UI on the main thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (favList != null) {
                            favItemAdapter = new FavItemAdapter(favList);
                            favListView.setAdapter(favItemAdapter);

                            favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != -1) {
                                        long shopId = favList.get(i).getId();

                                        // Create a bundle to pass data to FoodItemFragment
                                        Bundle bundle = new Bundle();
                                        bundle.putLong("shopId", shopId);
                                        bundle.putString("email", email);

                                        // Create a new instance of FoodItemFragment
                                        RestaurantFragment resItemFragment = new RestaurantFragment();
                                        resItemFragment.setArguments(bundle);

                                        // Navigate to FoodItemFragment
                                        getParentFragmentManager().beginTransaction()
                                                .replace(R.id.relativelayout, resItemFragment)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}




