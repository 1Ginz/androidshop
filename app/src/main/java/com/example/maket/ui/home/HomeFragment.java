package com.example.maket.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Adapter.ClothesAdapter;
import com.example.maket.Adapter.ImagesAdapter;
import com.example.maket.BottomSheet;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Database.OrderDatabase;
import com.example.maket.Entity.ClothesItem;
import com.example.maket.Entity.Order;
import com.example.maket.Model.Image;
import com.example.maket.R;


import java.util.ArrayList;

public class HomeFragment extends Fragment implements BottomSheet.BottomSheetListener {
    private GridView mGridViewIntro;
    private RecyclerView mRecyclerViewFood;
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private ImagesAdapter images_adapter;
    private ClothesAdapter clothesAdapter;
    private HomeViewModel homeViewModel;
    private EditText mSearchView;
    Context context;
    private AppDatabase db;
    private ArrayList<ClothesItem> foodies;


    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) { homeViewModel
        = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mGridViewIntro = root.findViewById(R.id.gv_intro);
        mRecyclerViewFood = root.findViewById(R.id.rcl_clothes);
        mSearchView = root.findViewById(R.id.sv_serch);
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        mapping();
        images_adapter = new ImagesAdapter(getContext(), R.layout.images_intro, imageArrayList);
        mGridViewIntro.setAdapter(images_adapter);
        mapping();
        // Set Recy
        db = AppDatabase.getInstance(getContext());

        foodies = (ArrayList<ClothesItem>) db.daoFood().Clothes_LIST();
        clothesAdapter = new ClothesAdapter((ArrayList<ClothesItem>) foodies, getContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerViewFood.setLayoutManager(manager);
        mRecyclerViewFood.setAdapter(clothesAdapter);
        //Set Recy
        clothesAdapter.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int Position) {
                Toast.makeText(getContext(), ""+Position, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn muốn ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<ClothesItem> foodies1 = (ArrayList<ClothesItem>) db.daoFood().Clothes_LIST();
                        ClothesItem clothesItem = foodies1.get(Position);
                        db.daoFood().deleteFooy(clothesItem);
                        try {
                            foodies = (ArrayList<ClothesItem>) db.daoFood().Clothes_LIST();
                            clothesAdapter = new ClothesAdapter((ArrayList<ClothesItem>) foodies, getContext());
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
                            mRecyclerViewFood.setLayoutManager(manager);
                            mRecyclerViewFood.setAdapter(clothesAdapter);
                        }catch (Exception e){
                            Log.e("ERRO",""+e);
                        }
                        Toast.makeText(getContext(), "Đã xóa "+ clothesItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }

            @Override
            public void deleteItem(final int Position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Mua hàng");
                builder.setPositiveButton("Mua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClothesItem clothesItem = foodies.get(Position);
                        Order order = new Order();
                        order.setName(clothesItem.getName());
                        order.setPrice(clothesItem.getPrice());
                        order.setImage(clothesItem.getImage());
                        OrderDatabase database = OrderDatabase.getInstance(getContext());
                        database.daoOrder().insertorder(order);
                        Toast.makeText(getContext(), "Đã thêm vào đơn hàng ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return root;
    }

    // tim kiem tren recyc
    private void filter(String Text) {
        ArrayList<ClothesItem> clothesItemList = new ArrayList<>();
        for (ClothesItem clothesItem : foodies) {
            if (clothesItem.getName().toLowerCase().contains(Text.toLowerCase())) {
                clothesItemList.add(clothesItem);
            }
        }
        clothesAdapter.FilterList(clothesItemList);
    }

    // tim kiem tren recyc
    private void mapping() {
        imageArrayList.add(new Image("Bộ sưu tập mùa hè", R.drawable.anh1));
        imageArrayList.add(new Image("Giảm giá 30%", R.drawable.anh2));
        imageArrayList.add(new Image("Freeship áo hoodie", R.drawable.anh3));
        imageArrayList.add(new Image("Áo thun dài tay", R.drawable.anh4));
        imageArrayList.add(new Image("Quần Cargo Pant", R.drawable.anh5));
        imageArrayList.add(new Image("Áo thun mùa hè", R.drawable.anh6));
        imageArrayList.add(new Image("Chân váy mùa hè", R.drawable.anh7));
        imageArrayList.add(new Image("Váy trẻ em", R.drawable.anh8));
        imageArrayList.add(new Image("Quần jean năng động", R.drawable.anh9));
        imageArrayList.add(new Image("Cargo Pant khỏe khắn", R.drawable.anh10));
        imageArrayList.add(new Image("Đầm body", R.drawable.anh11));
        imageArrayList.add(new Image("Túi đeo chéo", R.drawable.anh12));
    }

    @Override
    public void OnitemClick(String text) {
        Toast.makeText(getContext(), " lol " + text, Toast.LENGTH_SHORT).show();
    }
}
