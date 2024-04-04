package com.example.w24_3175_g12_one_clickeat.Fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.ShopAdapter;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscoverFragment extends Fragment {

    OneClickEatDatabase ocdb;
    ShopAdapter shopAdapter;
    ListView listView;
    List<Shop> shopList = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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
        }

        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                shopList = ocdb.shopDao().getAllShops();
                // Don't initialize listView here
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        listView = view.findViewById(R.id.discoverView);
        // Initialize listView here

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the adapter and item click listener after the view has been created
        shopAdapter = new ShopAdapter(shopList);
        listView.setAdapter(shopAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == -1) {
                    return;
                }
                Toast.makeText(getActivity(), shopList.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
