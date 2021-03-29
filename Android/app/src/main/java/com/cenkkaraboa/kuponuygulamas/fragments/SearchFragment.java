package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.LoginActivity;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.adapters.CategoryAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SearchAdapter;
import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Register;
import com.cenkkaraboa.kuponuygulamas.models.Search;

import java.util.List;

import javax.sql.StatementEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class SearchFragment extends Fragment implements SearchAdapter.OnItemClickListener {

    TextView de, de2, de3;
    View view;
    TextView count;
    RecyclerView recyclerView;
    String ID;
    int val=0;
    int search;
    SearchAdapter searchAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        de = view.findViewById(R.id.de);
        de2 = view.findViewById(R.id.de2);
        count = view.findViewById(R.id.count);
        de3 = view.findViewById(R.id.de3);
        recyclerView = view.findViewById(R.id.recyclerView);


        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                de.setBackgroundResource(R.drawable.txt_red);
            }
        });


        de2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                de2.setBackgroundResource(R.drawable.txt_red);
            }
        });


        de3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                de3.setBackgroundResource(R.drawable.txt_red);
            }
            });


        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        ID=preferences.getString("ID","null");
        search=preferences.getInt("search",-1);

        System.out.println(ID+" ASDA" + "asdıjuodsajıoujımkdlsaıklojmdsapl");

        count.setText(search+" adet aranacak kelime hakkkınız kaldı");
        load();
        return view;
        }

        public void load(){

            Callback<List<Search>> listCallBack = new Callback<List<Search>>() {
                @Override
                public void onResponse(Call<List<Search>> call, Response<List<Search>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().size() > 0 ){
                            searchAdapter = new SearchAdapter(response.body(),getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                            recyclerView.setAdapter(searchAdapter);
                            searchAdapter.setOnItemClickListener(SearchFragment.this);
                            searchAdapter.notifyDataSetChanged();
                            val=response.body().size();
                            count.setText((search-response.body().size())+" adet aranacak kelime hakkkınız kaldı");

                        }else {
                            searchAdapter = new SearchAdapter(response.body(),getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                            recyclerView.setAdapter(searchAdapter);
                            searchAdapter.setOnItemClickListener(SearchFragment.this);
                            searchAdapter.notifyDataSetChanged();
                            val=0;
                            count.setText((search)+" adet aranacak kelime hakkkınız kaldı");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Search>> call, Throwable t) {
                }
            };
            interfaces.searchtList("search",ID,"").enqueue(listCallBack);
        }

        public void add(String add){
            if(val==search){
                Toast.makeText(getContext(),"Sınıra Ulaşıldı",Toast.LENGTH_SHORT).show();
            }else {
                Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResult()) {
                                    load();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        System.out.println(t.getCause());
                        System.out.println(t.getMessage() + "cececece");
                    }
                };
                interfaces.searchSave("search", ID, add).enqueue(listCallBack);
            }

        }
    @Override
    public void onItemClick(String position,int cou) {
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
        interfaces.searchDelete("search",ID,position).enqueue(listCallBack);
    }

    public void load2(){

            Callback<List<Search>> listCallBack = new Callback<List<Search>>() {
                @Override
                public void onResponse(Call<List<Search>> call, Response<List<Search>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().size() > 0 ){
                            searchAdapter = new SearchAdapter(response.body(),getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                            recyclerView.setAdapter(searchAdapter);
                            searchAdapter.setOnItemClickListener(SearchFragment.this);
                            searchAdapter.notifyDataSetChanged();
                            val=response.body().size();
                            count.setText((search-response.body().size())+" adet aranacak kelime hakkkınız kaldı");

                        }else {
                            searchAdapter = new SearchAdapter(response.body(),getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                            recyclerView.setAdapter(searchAdapter);
                            searchAdapter.setOnItemClickListener(SearchFragment.this);
                            searchAdapter.notifyDataSetChanged();
                            val=0;
                            count.setText((search)+" adet aranacak kelime hakkkınız kaldı");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Search>> call, Throwable t) {
                }
            };
            interfaces.searchtList("search",ID,"").enqueue(listCallBack);
        }

}