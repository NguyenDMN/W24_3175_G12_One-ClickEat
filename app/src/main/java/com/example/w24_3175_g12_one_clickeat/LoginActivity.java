package com.example.w24_3175_g12_one_clickeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    OneClickEatDatabase ocdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.btnLogin);
        ocdb = Room.databaseBuilder(getApplicationContext(),OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView email = findViewById(R.id.edEmail);
                TextView password = findViewById(R.id.edPassword);
                System.out.println(email);
                final String[] resultemail = {""};
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            resultemail[0] = ocdb.userDao().GetOneUser(email.getText().toString(), password.getText().toString()).getEmail();
                        }catch (Exception ex) {
                            Log.d("LOGIN ERROR", ex.getMessage());
                        }
                    }

                });
                if(resultemail[0].length()!=0){
                    System.out.println("123");
                    startActivity(new Intent(LoginActivity.this, DiscoverActivity.class));
                } else {
                    System.out.println(resultemail);
                    Toast.makeText(LoginActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
