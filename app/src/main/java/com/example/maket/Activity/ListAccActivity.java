package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maket.Database.AccountDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.R;

import java.util.List;

public class ListAccActivity extends AppCompatActivity {
    ListView mlistView;
    Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acc);

        mlistView= findViewById(R.id.list);
        AccountDatabase database = AccountDatabase.getInstance(getApplicationContext());
        List<Account> accounts = database.daoAccount().ACCOUNT_LIST();

//        List <String> stringList = null;
//        for (Account account : accounts){
//            String n = account.getUser();
//            stringList.add(n);
//        }

//        ArrayAdapter adapter = new ArrayAdapter<String>(
//                ListAccActivity.this,android.R.layout.simple_list_item_1,stringList);
//
//        mlistView.setAdapter(adapter);
    }
}