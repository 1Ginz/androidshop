package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.maket.Adapter.HistoryOrderAdapter;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Entity.Buy;
import com.example.maket.R;

import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity {

    Context context;
    private HistoryOrderAdapter historyOrderAdapter;
    private RecyclerView historyOrderRecyleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        historyOrderRecyleView = findViewById(R.id.historyOrderRecyleView);
        final BuyDatabase database = BuyDatabase.getInstance(getApplicationContext());
        List<Buy> orderArrayList =  database.daoBuy().BUY_LIST();
        historyOrderAdapter = new HistoryOrderAdapter(orderArrayList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        historyOrderRecyleView.setLayoutManager(manager);
        historyOrderRecyleView.setAdapter(historyOrderAdapter);
    }
}