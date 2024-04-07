package com.example.w24_3175_g12_one_clickeat.interfaces;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.w24_3175_g12_one_clickeat.model.User;

import java.util.List;
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertUsersFromList(List<User> userList);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertOneUser(User user);

    @Query("SELECT * from users")
    List<User> GetAllUsers();

    @Query("SELECT * from users where email = :email and password = :password")
    User GetOneUser(String email,String password);

    @Query("SELECT * from users where email = :email")
    User GetOneUserByEmail(String email);
    @Query("Delete from users where email = :email")
    int deleteOneUserWithEmail(String email);

    @Query("Update users SET imageuri = :imageuri where email = :email ")
    void updateImageUriByEmail(String imageuri, String email);
}
