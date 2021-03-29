package com.cenkkaraboa.kuponuygulamas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.models.Coupon;
import com.cenkkaraboa.kuponuygulamas.models.Like;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.Holder> {
    public List<Coupon> itemList;
    public Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Integer position,boolean value);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CouponAdapter(List<Coupon> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;


    }

    @Override
    public CouponAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CouponAdapter.Holder holder, int position) {
       try{


           holder.title_head.setText(itemList.get(position).getName());
           holder.title_content.setText(itemList.get(position).getDescription());
           if(itemList.get(position).getUrl().toString().length()>0){
               holder.go.setText("SATIN AL");
           }else{
               holder.go.setText("KOPYALA");
           }



       }catch (Exception ignored){

       }

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }





    public class Holder extends RecyclerView.ViewHolder {
        TextView title_head,title_content,go;

        public Holder(final View itemView) {
            super(itemView);
            title_head =  itemView.findViewById(R.id.title_head);
            title_content =  itemView.findViewById(R.id.title_content);
            go =  itemView.findViewById(R.id.go);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            if(itemList.get(position).getUrl().toString().length()>0){
                                mListener.onItemClick((position),true);
                            }else{
                                mListener.onItemClick((position),false);
                            }

                        }
                    }
                }
            });

        }
    }

}