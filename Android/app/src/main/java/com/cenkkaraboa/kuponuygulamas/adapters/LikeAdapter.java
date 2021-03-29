package com.cenkkaraboa.kuponuygulamas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.cenkkaraboa.kuponuygulamas.models.Like;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.Holder>  implements Filterable {
    public List<Like> itemList;
    public List<Like> totalList;
    public Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String position,boolean value);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public LikeAdapter(List<Like> itemList, Context context) {
        this.itemList = itemList;
        this.totalList = new ArrayList<>(itemList);
        this.context = context;


    }

    @Override
    public LikeAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(LikeAdapter.Holder holder, int position) {
       try{


           holder.name.setText(itemList.get(position).getName());

           String [] a=itemList.get(position).getImage().split(",");
                 Picasso.with(context)
                        .load(a[0])
                        .into(holder.image);


       }catch (Exception ignored){

       }

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

                List<Like> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(totalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Like item : totalList) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                results.values = filteredList;

                return results;



        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                if(itemList != null){
                    itemList.clear();
                }
                itemList.addAll((List) results.values);
                notifyDataSetChanged();
        }
    };




    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image,delete;

        public Holder(final View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.name);
            image =  itemView.findViewById(R.id.image);
            delete =  itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemList.get(position).getId(),false);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(itemList.get(position).getId(),true);
                        }
                    }
                }
            });
        }
    }

}