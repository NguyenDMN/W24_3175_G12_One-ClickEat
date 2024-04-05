package com.example.w24_3175_g12_one_clickeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.Item;
import com.example.w24_3175_g12_one_clickeat.model.Shop;
import com.example.w24_3175_g12_one_clickeat.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
//hi
    List<User> users = new ArrayList<>();
    List<Shop> shopList = new ArrayList<>();
    List<Item> itemList = new ArrayList<>();
    OneClickEatDatabase ocdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadUserCSV();
        insertShops();
        Button btnLogin = findViewById(R.id.btLogin);
        Button btnSignUp = findViewById(R.id.btRegister);
        ocdb = Room.databaseBuilder(getApplicationContext(),OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ocdb.userDao().insertUsersFromList(users);
                ocdb.shopDao().deleteAllShops();
                ocdb.itemDao().deleteAllItems();
                ocdb.shopDao().insertShopsFromList(shopList);
                Shop jolibee = ocdb.shopDao().getOneShopByName("jolibee");
                Shop mc = ocdb.shopDao().getOneShopByName("mcdonald");
                Shop wendy = ocdb.shopDao().getOneShopByName("Wendys");
                Shop subway = ocdb.shopDao().getOneShopByName("Subway");
                Shop poke = ocdb.shopDao().getOneShopByName("Poke");
                Log.d("GETJOLI", String.valueOf(jolibee.getId()));
                itemList.add(new Item("setA", "burger", 15.88,jolibee.getId(), R.drawable.jolibee1 ));
                itemList.add(new Item("setB", "burger and coke", 20.99, jolibee.getId(), R.drawable.jolibee2));
                itemList.add(new Item("setC", "noodles and coke", 10.99, jolibee.getId(), R.drawable.jolibee3));
                itemList.add(new Item("mcA", "burger", 7.88,mc.getId(), R.drawable.mc1 ));
                itemList.add(new Item("mcB", "burger double meat", 6.99, mc.getId(), R.drawable.mc2));
                itemList.add(new Item("mcC", "fries", 2.99, mc.getId(), R.drawable.mc3));
                itemList.add(new Item("wendyA", "A", 7.88,wendy.getId(), R.drawable.wendy1 ));
                itemList.add(new Item("wendyB", "B", 6.99, wendy.getId(), R.drawable.wendy2));
                itemList.add(new Item("wendyC", "C", 2.99, wendy.getId(), R.drawable.wendy3));
                itemList.add(new Item("subwayA", "A", 7.88,subway.getId(), R.drawable.subway1 ));
                itemList.add(new Item("subwayB", "B", 6.99, subway.getId(), R.drawable.subway2));
                itemList.add(new Item("subwayC", "C", 2.99, subway.getId(), R.drawable.subway3));
                itemList.add(new Item("pokeA", "A", 7.88,poke.getId(), R.drawable.poke1 ));
                itemList.add(new Item("pokeB", "B", 6.99, poke.getId(), R.drawable.poke2));
                itemList.add(new Item("pokeC", "C", 2.99, poke.getId(), R.drawable.poke3));

                ocdb.itemDao().insertItemsFromList(itemList);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    public void insertShops() {
        Log.d("INSERTSHOPS" , "insert shops");
        shopList = new ArrayList<>();
        Shop jolibee = new Shop("jolibee", "jolibee shop", 4.3, "30min", R.drawable.jolibee);

        shopList.add(jolibee);
        shopList.add(new Shop("mcdonald", "mcdonald shop", 4.5, "10min", R.drawable.mcdonald));
        shopList.add(new Shop("Subway", "Subway shop", 3.8, "20min", R.drawable.subway));
        shopList.add(new Shop("Wendys", "Wendys shop", 4.3, "50min", R.drawable.wendys));
        shopList.add(new Shop("Poke", "poke shop", 4, "40min", R.drawable.poke));

    }


    public void ReadUserCSV() {
        users = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.user);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String userLine;

        try{
            while((userLine = reader.readLine())!=null) {
                String[] dataField = userLine.split(",");
                Log.d("DATAFIELD", dataField[0] + dataField[1] + dataField[2] + dataField[3]);
                User eachUser = new User(dataField[0], dataField[1], dataField[2], dataField[3]);
                users.add(eachUser);
            }
        }catch (Exception ex) {

        }finally {
            try{
                inputStream.close();
            }catch (IOException ex) {

            }
        }

    }
}
