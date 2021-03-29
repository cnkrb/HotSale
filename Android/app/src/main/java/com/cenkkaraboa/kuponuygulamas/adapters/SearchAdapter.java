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
import com.cenkkaraboa.kuponuygulamas.models.Search;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder>  implements Filterable {
    public List<Search> itemList;
    public List<Search> totalList;
    public Context context;
    private OnItemClickListener mListener;
    public int count=-1;
    public int index=0;
    SharedPreferences preferences;


    public interface OnItemClickListener {
        void onItemClick(String position,int cou);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SearchAdapter(List<Search> itemList, Context context) {
        this.itemList = itemList;
        this.totalList = new ArrayList<>(itemList);
        this.context = context;
        preferences= PreferenceManager.getDefaultSharedPreferences(this.context);
        count=preferences.getInt("search",-1);
    }

    @Override
    public SearchAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SearchAdapter.Holder holder, int position) {
       try{
           holder.title.setText(itemList.get(position).getName());


           holder.home.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   index++;
                   holder.home.setStrokeColor(R.color.black);
                   holder.home.setStrokeWidth(4);


               }
           });

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

                List<Search> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(totalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Search item : totalList) {
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
        MaterialCardView home;
        LinearLayout delete;
        TextView title;
        public Holder(final View itemView) {
            super(itemView);
            home =  itemView.findViewById(R.id.home);
            title =  itemView.findViewById(R.id.title);
            delete =  itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemList.get(position).getId(),count-index);
                        }

                    }
                }
            });
        }
    }

}