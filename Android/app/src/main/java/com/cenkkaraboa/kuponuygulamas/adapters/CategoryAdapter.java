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
import android.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder>  implements Filterable {
    public List<Category> itemList;
    public List<Category> totalList;
    public Context context;
    private OnItemClickListener mListener;
    public int count=-1;
    public int index=0;
    SharedPreferences preferences;

    public interface OnItemClickListener {
        void onItemClick(String position,String selected,int cou);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CategoryAdapter(List<Category> itemList, Context context) {
        this.itemList = itemList;
        this.totalList = new ArrayList<>(itemList);
        this.context = context;
        preferences= PreferenceManager.getDefaultSharedPreferences(this.context);
        count=preferences.getInt("category",-1);

    }

    @Override
    public CategoryAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CategoryAdapter.Holder holder, int position) {
       try{


           holder.title.setText(itemList.get(position).getName());
           Random rand = new Random();
           int rand_int1 = rand.nextInt(5);
           System.out.println(rand_int1+"randomsayı");
           if( (position %2) ==0){
               holder.image.setBackgroundResource(R.color.bg_1);
           }
          else{
               holder.image.setBackgroundResource(R.color.bg_2);
           }




           if(itemList.get(position).getSelected().equals("1")){
               holder.cardView.setStrokeColor(R.color.black);
               System.out.println("girdiii");
               index++;
               holder.cardView.setStrokeWidth(4);
           }else {
               holder.cardView.setStrokeColor(null);
               holder.cardView.setStrokeWidth(0);
           }

           Picasso.with(context)
                   .load(itemList.get(position).getImage())
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

                List<Category> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(totalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Category item : totalList) {
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
        TextView title;
        ImageView image;
        MaterialCardView cardView;
        LinearLayout home;
        public Holder(final View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title);
            image =  itemView.findViewById(R.id.image);
            home =  itemView.findViewById(R.id.home);
            cardView =  itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if(index==5){
                                if(itemList.get(position).getSelected().equals("1")){
                                    index--;
                                    mListener.onItemClick(itemList.get(position).getId(),itemList.get(position).getSelected(),count-index);
                                }else {
                                    Toast.makeText(context,"kategori seçme sınırına ulaşıldı",Toast.LENGTH_SHORT).show();

                                }
                            }else {
                                if(itemList.get(position).getSelected().equals("1")){
                                    index--;
                                }
                                mListener.onItemClick(itemList.get(position).getId(),itemList.get(position).getSelected(),count-index);


                            }
                        }
                    }
                }
            });
        }
    }

}