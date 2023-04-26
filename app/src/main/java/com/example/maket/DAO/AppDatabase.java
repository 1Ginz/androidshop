package com.example.maket.DAO;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maket.Entity.ClothesItem;


@Database(entities = ClothesItem.class,version = 1,exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoItem daoFood();

    private static final String DB_NAME="food_db";

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null ){
            instance=Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME).
                    fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
