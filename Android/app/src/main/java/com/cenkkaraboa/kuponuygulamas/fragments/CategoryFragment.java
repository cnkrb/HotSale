package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.adapters.CategoryAdapter;
import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class CategoryFragment extends Fragment implements CategoryAdapter.OnItemClickListener {
    String ID;
    int category;
    View view;
    RecyclerView recyclerView;
    TextView count;
    boolean value;
    public static CategoryAdapter categoryAdapter;


    public CategoryFragment(Boolean value){
        this.value=value;
    }

    public CategoryFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        count=view.findViewById(R.id.count);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        ID=preferences.getString("ID","null");
        System.out.println(ID+" ASDA" + "asdıjuodsajıoujımkdlsaıklojmdsapl");
        load();
        category=preferences.getInt("category",-1);
/*
        count.setText(category+" adet kategori seçme hakkınız kaldı");
*/
        return view;
    }

    public void load(){
        Callback<List<Category>> listCallBack = new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0 ){
                        categoryAdapter = new CategoryAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                        recyclerView.setAdapter(categoryAdapter);
                        categoryAdapter.setOnItemClickListener(CategoryFragment.this);
                        categoryAdapter.notifyDataSetChanged();
                        System.out.println(ID+"ASDA"+response.body().size() + "asdıjuodsajıoujımkdlsaıklojmdsapl");
                        int c=0;
                        for(int i=0;i<response.body().size();i++){
                            if(response.body().get(i).getSelected().equals("1")){
                                c++;
                            }
                        }
                        count.setText(category-c+" adet kategori seçme hakkınız kaldı");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        };
        interfaces.categoryList("category",ID,"").enqueue(listCallBack);
    }

    @Override
    public void onItemClick(String position,String selected,int cou) {
        if(selected.equals("1")){
            Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getResult()){
                            load();
                            count.setText(cou+" adet kategori seçme hakkınız kaldı");
                        }
                    }
                }
                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                }
            };
            interfaces.categoryDelete("category",ID,position).enqueue(listCallBack);
        }else {
            Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getResult()){
                            load();
                            count.setText(cou+" adet kategori seçme hakkınız kaldı");
                        }
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                }
            };
            interfaces.categorySave("category",ID,position).enqueue(listCallBack);
        }

    }
}