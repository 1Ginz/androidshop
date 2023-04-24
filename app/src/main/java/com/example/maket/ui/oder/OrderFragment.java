package com.example.maket.ui.oder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Adapter.OderAdapter;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Database.OrderDatabase;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.Order;
import com.example.maket.R;
import com.example.maket.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private TextView textView;
    private OrderViewModel orderViewModel;
    private RecyclerView mRecyclerView;
    Button button;
    OderAdapter oderAdapter;
    List<Order> orderArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        mRecyclerView =root.findViewById(R.id.rcl_oder);
        textView= root.findViewById(R.id.tv_thanhtoan);
        button=root.findViewById(R.id.btn_thanhtoan);

         final OrderDatabase database = OrderDatabase.getInstance(getContext());
        orderArrayList =  database.daoOrder().ORDERS_LIST();
        Double sum = 0.0;
        for (Order order : orderArrayList){
            sum += order.getPrice();
        }

        textView.setText(String.valueOf(sum)+" vnđ");
        oderAdapter = new OderAdapter(getContext(),orderArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(oderAdapter);
        final Double finalSum1 = sum;
        oderAdapter.setOnItemClickListener(new OderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int Position) {

            }
            @Override
            public void deleteItem(int Position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final OrderDatabase database1= OrderDatabase.getInstance(getContext());
                ArrayList<Order> orders = (ArrayList<Order>) database1.daoOrder().ORDERS_LIST();
                final Order order = orders.get(Position);
                builder.setTitle("Bạn có muốn xóa "+order.getName()+" không ?");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            database1.daoOrder().deleteorder(order);
                            ArrayList<Order> orders1 = (ArrayList<Order>) database1.daoOrder()
                                    .ORDERS_LIST();
                            double prc = order.getPrice();
                            OderAdapter oderAdapter = new OderAdapter(getContext(),orders1);
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(manager);
                            mRecyclerView.setAdapter(oderAdapter);
                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            double summ = finalSum1 -prc;
                            textView.setText(String.valueOf(summ));

                        }catch (Exception e){
                            Log.e("ERRO",""+e);
                        }
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        final Double finalSum = sum;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyDatabase buyDatabase = BuyDatabase.getInstance(getContext());
                Buy buy =new Buy();
                buy.setPrice(finalSum);
                buyDatabase.daoBuy().insertorder(buy);
                Toast.makeText(getContext(), "Mua hàng thành công", Toast.LENGTH_SHORT).show();
                textView.setText("0 VNĐ");
                try {
                    OrderDatabase database1 =OrderDatabase.getInstance(getContext());
                    database1.daoOrder().deleteall();
                    ArrayList<Order> orders = (ArrayList<Order>) database1.daoOrder().ORDERS_LIST();
                    oderAdapter = new OderAdapter(getContext(),orders);
                    mRecyclerView.setAdapter(oderAdapter);
                }catch (Exception e){
                    Log.e("ERRO",""+e );
                }
//                Intent intent =new Intent(getContext(), HomeFragment.class);
//                startActivity(intent);


            }
        });
        return root;
    }
}