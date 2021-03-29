package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.adapters.LikeAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SearchAdapter;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Like;
import com.cenkkaraboa.kuponuygulamas.models.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;

public class LikeFragment extends Fragment implements LikeAdapter.OnItemClickListener {

    View view;
    String ID;
    RecyclerView recyclerView;
   public static LikeAdapter likeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_like, container, false);

       SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
       ID=preferences.getString("ID","null");


       recyclerView=view.findViewById(R.id.recyclerView);

       load();
       return view;
    }

    public void load(){
        Callback<List<Like>> listCallBack = new Callback<List<Like>>() {
            @Override
            public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0 ){
                        likeAdapter = new LikeAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(likeAdapter);
                        likeAdapter.setOnItemClickListener(LikeFragment.this);
                        likeAdapter.notifyDataSetChanged();
                    }else {
                        if(likeAdapter != null){
                            likeAdapter = new LikeAdapter(response.body(),getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                            recyclerView.setAdapter(likeAdapter);
                            likeAdapter.setOnItemClickListener(LikeFragment.this);
                            likeAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Like>> call, Throwable t) {
            }
        };
        interfaces.likeList("like",ID,"").enqueue(listCallBack);
    }

    @Override
    public void onItemClick(String position, boolean value) {
        if(value){

        }else {
            Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        if(response.body().getResult()){
                            load();
                        }
                    }
                }
                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    System.out.println(t.getCause());
                    System.out.println(t.getMessage()+"cececece");
                }
            };
            interfaces.likeDelete("like",ID,position).enqueue(listCallBack);
        }
    }
}