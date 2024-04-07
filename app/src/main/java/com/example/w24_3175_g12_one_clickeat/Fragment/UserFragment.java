package com.example.w24_3175_g12_one_clickeat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.adpater.CartItemAdapter;
import com.example.w24_3175_g12_one_clickeat.adpater.HistoryAdapter;
import com.example.w24_3175_g12_one_clickeat.adpater.ShopAdapter;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Order;
import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String email;
    ListView orderListView;

    HistoryAdapter historyAdapter;

    List<OrderHistory> orderHistoryList = new ArrayList<>();

    OneClickEatDatabase ocdb;
    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView txtUserEmail = view.findViewById(R.id.txtUserEmail);
        View headerView = inflater.inflate(R.layout.list_header_cart, null);
        orderListView = view.findViewById(R.id.user_fragment_listView);
        orderListView.addHeaderView(headerView);
        txtUserEmail.setText("User Email: " + email);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        // Perform database operations asynchronously using ExecutorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();


        executorService.execute(new Runnable() {

            @Override
            public void run() {
                orderHistoryList = ocdb.orderHistoryDao().getAllOrderHistoriesByEmail(email);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        historyAdapter = new HistoryAdapter(orderHistoryList);
                        orderListView.setAdapter(historyAdapter);
                    }
                });


            }
        });


    }
}
