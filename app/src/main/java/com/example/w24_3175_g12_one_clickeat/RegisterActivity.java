package com.example.w24_3175_g12_one_clickeat;

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

import com.example.w24_3175_g12_one_clickeat.databases.OneClickEatDatabase;
import com.example.w24_3175_g12_one_clickeat.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    OneClickEatDatabase ocdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.btnRegister);
        ocdb = Room.databaseBuilder(getApplicationContext(), OneClickEatDatabase.class, "oneclickeat.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = findViewById(R.id.registerEmailTxt);
                EditText password = findViewById(R.id.registerPasswordTxt);
                final User[] user = {null};
                // Toast.makeText(RegisterActivity.this, "Email already exists" + email.getText().toString(), Toast.LENGTH_SHORT).show();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        user[0] = ocdb.userDao().GetOneUserByEmail(email.getText().toString());

                        // Move the following code inside this run method
                        if(user[0] == null) {
                            ocdb.userDao().insertOneUser(new User(email.getText().toString(),password.getText().toString()));
                            startActivity(new Intent(RegisterActivity.this, DiscoverActivity.class));
                            Bundle bundle = new Bundle();
                            bundle.putString("email", email.getText().toString());
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });



            }
        });

    }
}
