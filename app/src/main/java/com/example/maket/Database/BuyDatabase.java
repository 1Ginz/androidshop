package com.example.maket.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maket.DAO.DaoBuy;
import com.example.maket.DAO.DaoOrder;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.Order;

@Database(entities = Buy.class,version = 1)
public abstract class BuyDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="buy_db";
    public abstract DaoBuy daoBuy();
    private static BuyDatabase instance;

    public static synchronized BuyDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), BuyDatabase.class
                    ,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
