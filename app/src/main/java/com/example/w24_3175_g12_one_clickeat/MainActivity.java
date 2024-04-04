package com.example.w24_3175_g12_one_clickeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
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
    OneClickEatDatabase ocdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadUserCSV();
        Button btnLogin = findViewById(R.id.btLogin);
        Button btnSignUp = findViewById(R.id.btRegister);
        ocdb = Room.databaseBuilder(getApplicationContext(),OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ocdb.userDao().insertUsersFromList(users);

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

    public void ReadUserCSV() {
        users = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.user);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String userLine;

        try{
            while((userLine = reader.readLine())!=null) {
                String[] dataField = userLine.split(",");
                User eachUser = new User(dataField[0], dataField[1]);
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
