package com.example.w24_3175_g12_one_clickeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.w24_3175_g12_one_clickeat.Fragment.CartFragment;
import com.example.w24_3175_g12_one_clickeat.Fragment.DiscoverFragment;
import com.example.w24_3175_g12_one_clickeat.Fragment.FavoriteFragment;
import com.example.w24_3175_g12_one_clickeat.Fragment.UserFragment;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    List<Shop> shopList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        bottomNavigationView = findViewById(R.id.bottomMenuBar);

        bottomNavigationView.setOnItemSelectedListener(this);
        ShopAdapter shopAdapter = new ShopAdapter(shopList);
        loadFragment(new DiscoverFragment());




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        if (item.getItemId() == R.id.menu_Discover){
            fragment = new DiscoverFragment();
        } else if (item.getItemId() == R.id.menu_Cart) {
            fragment = new CartFragment();
        } else if (item.getItemId()== R.id.menu_Favorite) {
            fragment = new FavoriteFragment();
        } else if (item.getItemId() == R.id.menu_User) {
            String userEmail = getIntent().getStringExtra("email");
            fragment = new UserFragment();
            bundle.putString("email", userEmail);
            fragment.setArguments(bundle);

        }

        if(fragment != null){
            loadFragment(fragment);
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    void loadFragment(Fragment fragment) {
        // Begin the transaction
        getSupportFragmentManager().beginTransaction()
                // Replace the contents of the container with the new fragment
                .replace(R.id.relativelayout, fragment)
                // Commit the transaction
                .commit();
    }
}
