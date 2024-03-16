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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DiscoverActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        bottomNavigationView = findViewById(R.id.bottomMenuBar);

        bottomNavigationView.setOnItemSelectedListener(this);

        loadFragment(new DiscoverFragment());




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.menu_Discover){
            fragment = new DiscoverFragment();
        } else if (item.getItemId() == R.id.menu_Cart) {
            fragment = new CartFragment();
        } else if (item.getItemId()== R.id.menu_Favorite) {
            fragment = new FavoriteFragment();
        } else if (item.getItemId() == R.id.menu_User) {
            fragment = new UserFragment();
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