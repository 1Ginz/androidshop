package com.example.maket.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.maket.Entity.ClothesItem;

import java.util.List;

@Dao
public interface DaoFood {

   @Query("Select * from food_db")
    List <ClothesItem> FOODY_LIST();
   @Insert
    void insertFoody( ClothesItem clothesItem);
   @Update
    void updateFoody( ClothesItem clothesItem);
   @Delete
    void deleteFooy(ClothesItem clothesItem);



}
