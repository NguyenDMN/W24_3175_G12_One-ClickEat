package com.example.w24_3175_g12_one_clickeat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w24_3175_g12_one_clickeat.R;
import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;

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
                EditText email = findViewById(R.id.edEmail);
                EditText password = findViewById(R.id.edPassword);
                System.out.println("email"  +email);
                final String[] resultemail = {""};
                Bundle bundle = new Bundle();
                // Toast.makeText(LoginActivity.this, email.getText().toString(), Toast.LENGTH_SHORT).show();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            resultemail[0] = ocdb.userDao().GetOneUser(email.getText().toString(), password.getText().toString()).getEmail();
                            Log.d("LOGIN ING", resultemail[0]);
                           if(resultemail[0] != null && resultemail[0].length() != 0){
                            Intent intent = new Intent(LoginActivity.this, DiscoverActivity.class);
                            intent.putExtra("email", resultemail[0]);
                            startActivity(intent);
                        } else {
                            System.out.println(resultemail);
                            Toast.makeText(LoginActivity.this, "User not exist" + resultemail[0], Toast.LENGTH_SHORT).show();
                        }
                        }catch (Exception ex) {
                            Log.d("LOGIN ERROR", ex.getMessage());
                        }
                    }

                });


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
