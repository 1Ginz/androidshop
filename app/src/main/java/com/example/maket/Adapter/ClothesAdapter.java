package com.example.maket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Convert.DataConvert;
import com.example.maket.Entity.ClothesItem;
import com.example.maket.R;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    ArrayList<ClothesItem> clothesItemArrayList;
    Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int Position);
        void deleteItem(int Position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        mListener=clickListener;
    }

    public ClothesAdapter(ArrayList<ClothesItem> clothesItemArrayList, Context context) {
        this.clothesItemArrayList = clothesItemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View Holder = inflater.inflate(R.layout.clothes_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(Holder,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothesItem clothesItem = clothesItemArrayList.get(position);
        holder.mImageViewFood.setImageBitmap(DataConvert.ConvertBitmap(clothesItem.getImage()));
        holder.mTextViewName.setText(clothesItem.getName());
        holder.mTextViewPrice.setText(clothesItem.getPrice()+" VNĐ");
        holder.mTextViewCategory.setText(clothesItem.getCategory());
        holder.mTextViewDetail.setText(clothesItem.getDetail());
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
            mImageViewFood=itemView.findViewById(R.id.img_clothes);
            mTextViewName=itemView.findViewById(R.id.tv_nameclothes);
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
        return clothesItemArrayList.size();
    }
    public void FilterList(ArrayList<ClothesItem> foodies){
        clothesItemArrayList = foodies;
        notifyDataSetChanged();
    }

}
