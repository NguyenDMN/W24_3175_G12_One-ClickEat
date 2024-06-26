package com.example.w24_3175_g12_one_clickeat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w24_3175_g12_one_clickeat.activity.RegisterActivity;
import com.example.w24_3175_g12_one_clickeat.adpater.CartItemAdapter;
import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.interfaces.OnItemRemovedListener;
import com.example.w24_3175_g12_one_clickeat.model.Order;
import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements OnItemRemovedListener {
    String email;

    OneClickEatDatabase ocdb;
    CartItemAdapter cartItemAdapter;

    ListView cartListView;

    List<Order> orderList = new ArrayList<>();


    TextView finalTotalPrice;

    Button btnMakeOrder;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double tmpSum = 0;

    @Override
    public void onItemRemoved(List<Order> orderList) {
        // Refresh your fragment's UI here
        // For example, you can re-fetch the data and update the ListView
        // update adpapter
        final double[] sum = {0};
        if(orderList!= null){
            for(int i = 0; i<orderList.size();i++){
                double pricePerUnit = orderList.get(i).getPrice();
                long quantity = orderList.get(i).getQuantity();

                double totalPrice = pricePerUnit *quantity;
                sum[0] += totalPrice;
            }
        }
        DecimalFormat df = new DecimalFormat("$#.##");
        String finalTotalPriceFormat = df.format(sum[0]);
        Log.d("TOTALPRICE", finalTotalPriceFormat);
        finalTotalPrice.setText(finalTotalPriceFormat);

    }

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                orderList = ocdb.orderDao().getAllOrdersByEmail(email);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartListView = view.findViewById(R.id.cartView);
        finalTotalPrice = view.findViewById(R.id.txtViewFinalPrice);
        btnMakeOrder = view.findViewById(R.id.btnCheckOut);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        // Perform database operations asynchronously using ExecutorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        cartItemAdapter = new CartItemAdapter(ocdb,orderList, getContext());
        cartListView.setAdapter(cartItemAdapter);

        cartItemAdapter.setOnItemRemoveListener(this);
        final double[] sum = {0};
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                orderList = ocdb.orderDao().getAllOrdersByEmail(email);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if(orderList!= null){
                            for(int i = 0; i<orderList.size();i++){
                                double pricePerUnit = orderList.get(i).getPrice();
                                long quantity = orderList.get(i).getQuantity();

                                double totalPrice = pricePerUnit *quantity;
                                sum[0] += totalPrice;
                            }
                        }
                        DecimalFormat df = new DecimalFormat("$#.##");
                        String finalTotalPriceFormat = df.format(sum[0]);
                        finalTotalPrice.setText(finalTotalPriceFormat);
                    }
                });



                btnMakeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ORDERLIST", String.valueOf(orderList.size()));
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        Bundle bundle = new Bundle();
                        TextView price = view.findViewById(R.id.txtViewFinalPrice);
                        Double result = Double.parseDouble(price.getText().toString().replace("$", ""));
                        bundle.putString("email",email);
                        bundle.putDouble("finalPrice", result);

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                // Save the order history into the database
                                OrderHistory orderHistory = new OrderHistory(email, new Date(), result);
                                ocdb.orderHistoryDao().insertOrderHistory(orderHistory);

                                // Clear the order list for the current user
                                ocdb.orderDao().deleteOrderFromEmail(email);
                            }
                        });

                        CheckoutFragment checkoutFragment = new CheckoutFragment();
                        checkoutFragment.setArguments(bundle);
                        getParentFragmentManager().beginTransaction()
                            .replace(R.id.relativelayout,checkoutFragment)
                            .addToBackStack(null)
                            .commit();


                    }
                });


            }
        });


    }

}
