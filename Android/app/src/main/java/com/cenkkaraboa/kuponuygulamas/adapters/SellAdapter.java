package com.cenkkaraboa.kuponuygulamas.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SellAdapter extends RecyclerView.Adapter<SellAdapter.Holder> {
    public List<Product> itemList;
    public Context context;
    public Integer pos = -1;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public SellAdapter(List<Product> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }



    @Override
    public SellAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell, parent, false);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int devicewidth = (int) (displayMetrics.widthPixels * 0.75);
            view.getLayoutParams().width = devicewidth;

        return new SellAdapter.Holder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SellAdapter.Holder holder, int position) {

            if (position == pos) {
                holder.select.setText("Seçildi");
                holder.select.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0,R.drawable.ic_baseline_check_24_black, 0);
            } else {
                holder.select.setText("Seç");
                holder.select.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            }

        holder.name.setText(itemList.get(position).getName());
            String [] a=itemList.get(position).getImage().split(",");
        Picasso.with(context)
                .load(a[0])
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    void deneme(Integer positon) {
        pos = positon;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        Button select;

        public Holder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            select = itemView.findViewById(R.id.select);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemList.get(position).getId());
                            deneme(position);
                        }
                    }
                }
            });
        }
    }

}