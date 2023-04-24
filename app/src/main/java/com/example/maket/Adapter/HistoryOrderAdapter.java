package com.example.maket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Entity.Buy;
import com.example.maket.Entity.Order;
import com.example.maket.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder> {

    List<Buy> buyList;
    Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int Position);
        void deleteItem(int Position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        mListener=clickListener;
    }

    public HistoryOrderAdapter(List<Buy> buyList) {
        this.buyList = buyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View Holder = inflater.inflate(R.layout.viewholder_historyoder,parent,false);
        ViewHolder viewHolder = new ViewHolder(Holder,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buy buy = buyList.get(position);
        holder.orderIdTextView.setText(String.valueOf(buy.getId()));
        holder.totalTextView.setText(String.valueOf(buy.getPrice()));
//        holder.mTextViewName.setText(foody.getName());
//        holder.mTextViewPrice.setText(foody.getPrice()+" VNĐ");
//        holder.mTextViewCategory.setText(foody.getCategory());
//        holder.mTextViewDetail.setText(foody.getDetail());
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView orderIdTextView;
        TextView totalTextView;
        TextView mTextViewCategory;
        TextView mTextViewPrice;
        TextView mTextViewDetail;
        ImageView nImageViewAddOrder;
        public ViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIDTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
//            mTextViewName=itemView.findViewById(R.id.tv_namefood);
//            mTextViewCategory=itemView.findViewById(R.id.tv_category);
//            mTextViewPrice=itemView.findViewById(R.id.tv_price);
//            mTextViewDetail=itemView.findViewById(R.id.tv_detail);
//            nImageViewAddOrder=itemView.findViewById(R.id.imv_add_order);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//
//            });
//            nImageViewAddOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.deleteItem(position);
//                        }
//                    }
//                }
//            });
        }
    }
    @Override
    public int getItemCount() {
        return buyList.size();
    }
    public void FilterList(ArrayList<Buy> foodies){
        buyList = foodies;
        notifyDataSetChanged();
    }

}

