package com.example.maket.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Convert.DataConvert;
import com.example.maket.Entity.Foody;
import com.example.maket.Entity.Order;
import com.example.maket.R;

import java.util.ArrayList;
import java.util.List;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.ViewHolder> {

    private Context mContext;
    private List mListOrder;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int Position);
        void deleteItem(int Position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        mListener=clickListener;
    }

    public OderAdapter(Context mContext, List mListOrder) {
        this.mContext = mContext;
        this.mListOrder = mListOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewOrder = inflater.inflate(R.layout.item_oder,parent,false);
        ViewHolder holder = new ViewHolder(viewOrder,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = (Order) mListOrder.get(position);
        holder.mTextViewName.setText(order.getName());
        holder.mTextViewPrice.setText(String.valueOf(order.getPrice()));
        holder.mImageViewFood.setImageBitmap(DataConvert.ConvertBitmap(order.getImage()));

    }

    @Override
    public int getItemCount() {
        return mListOrder.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView mTextViewName;
        private TextView mTextViewPrice;
        private TextView mTextViewSoluong;
        private ImageView mImageViewFood;
        private ImageView mButtonDelete;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener onclickItem) {
            super(itemView);
            view=itemView;
            mImageViewFood=itemView.findViewById(R.id.imv_oder);
            mTextViewName = itemView.findViewById(R.id.tv_nameorder);
//            mTextViewSoluong=itemView.findViewById(R.id.tv_soluong_order);
            mTextViewPrice=itemView.findViewById(R.id.tv_price_order);
            mButtonDelete=itemView.findViewById(R.id.imv_removeOrder);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickItem != null){
                        int Position = getAdapterPosition();
                        if ((Position != RecyclerView.NO_POSITION)){
                            onclickItem.deleteItem(Position);
                        }
                    }
                }
            });
        }
    }


}
