package com.example.maket.DAO;


import android.content.Context;
import android.os.AsyncTask;
import android.text.PrecomputedText;

import androidx.core.text.PrecomputedTextCompat;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maket.Entity.Foody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.Result;


@Database(entities = Foody.class,version = 1,exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoFood daoFood();

    private static final String DB_NAME="foody_db";

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
