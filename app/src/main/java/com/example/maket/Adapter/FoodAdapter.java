package com.example.maket.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Convert.DataConvert;
import com.example.maket.Entity.Foody;
import com.example.maket.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    ArrayList<Foody> foodyArrayList;
    Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int Position);
        void deleteItem(int Position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        mListener=clickListener;
    }

    public FoodAdapter(ArrayList<Foody> foodyArrayList, Context context) {
        this.foodyArrayList = foodyArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View Holder = inflater.inflate(R.layout.food_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(Holder,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foody foody = foodyArrayList.get(position);
        holder.mImageViewFood.setImageBitmap(DataConvert.ConvertBitmap(foody.getImage()));
        holder.mTextViewName.setText(foody.getName());
        holder.mTextViewPrice.setText(foody.getPrice()+" VNĐ");
        holder.mTextViewCategory.setText(foody.getCategory());
        holder.mTextViewDetail.setText(foody.getDetail());
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView mImageViewFood;
        TextView mTextViewName;
        TextView mTextViewCategory;
        TextView mTextViewPrice;
        TextView mTextViewDetail;
        ImageView nImageViewAddOrder;
        public ViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            view=itemView;
            mImageViewFood=itemView.findViewById(R.id.img_food);
            mTextViewName=itemView.findViewById(R.id.tv_namefood);
            mTextViewCategory=itemView.findViewById(R.id.tv_category);
            mTextViewPrice=itemView.findViewById(R.id.tv_price);
            mTextViewDetail=itemView.findViewById(R.id.tv_detail);
            nImageViewAddOrder=itemView.findViewById(R.id.imv_add_order);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }

            });
            nImageViewAddOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.deleteItem(position);
                        }
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return foodyArrayList.size();
    }
    public void FilterList(ArrayList<Foody> foodies){
        foodyArrayList= foodies;
        notifyDataSetChanged();
    }

}
