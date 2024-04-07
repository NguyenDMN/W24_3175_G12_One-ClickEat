package com.example.w24_3175_g12_one_clickeat.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.*;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.adpater.CartItemAdapter;
import com.example.w24_3175_g12_one_clickeat.adpater.HistoryAdapter;
import com.example.w24_3175_g12_one_clickeat.adpater.ShopAdapter;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Order;
import com.example.w24_3175_g12_one_clickeat.model.OrderHistory;
import com.example.w24_3175_g12_one_clickeat.model.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private static final int DEFAULT_IMAGE_RES_ID = R.drawable.userinfo;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OneClickEatDatabase ocdb;
    ExecutorService executorService;

    private String email;
    ListView orderListView;
    ImageView image;

    HistoryAdapter historyAdapter;

    List<OrderHistory> orderHistoryList = new ArrayList<>();

    private static final int PICK_IMAGE = 1;
    private Bitmap selectedImage;
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
        image = view.findViewById(R.id.user_image_view);

        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Retrieve the image URI from the database
                String tmp = ocdb.userDao().GetOneUserByEmail(email).getImageUri();
                Uri uri = tmp == null ? null :  Uri.parse(tmp);
                if (uri != null) {
                    // If the URI is not null, set the image using setImageURI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            InputStream inputStream = null;
                            try {
                                inputStream = requireActivity().getContentResolver().openInputStream(uri);
                                Bitmap rawBitmap = BitmapFactory.decodeStream(inputStream);

                                int width = 700;
                                int height = 700;
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(rawBitmap, width, height, true);

                                image.setImageBitmap(resizedBitmap);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                } else {
                    // If the URI is null, set the default image resource
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageResource(R.drawable.userinfo);
                        }
                    });
                }
            }
        });

        // Set click listener for the image
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ocdb = Room.databaseBuilder(getContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        // Perform database operations asynchronously using ExecutorService
        executorService = Executors.newSingleThreadExecutor();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                Bitmap rawBitmap = BitmapFactory.decodeStream(inputStream);

                int width = 700;
                int height = 700;
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(rawBitmap, width, height, true);

                image.setImageBitmap(resizedBitmap);
                // Update the image URI in the database
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        ocdb.userDao().updateImageUriByEmail(imageUri.toString(), email);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
