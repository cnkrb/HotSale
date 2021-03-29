package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.adapters.ProductAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SearchAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SellAdapter;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Product;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class SellFragment extends Fragment implements SellAdapter.OnItemClickListener {


    String id="-1";
    View view;
    TextView go,name,address,number,email;
    RecyclerView recyclerView;
    String ID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_sell, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        go=view.findViewById(R.id.go);
        name=view.findViewById(R.id.name);
        address=view.findViewById(R.id.address);
        number=view.findViewById(R.id.number);
        email=view.findViewById(R.id.email);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        ID=preferences.getString("ID","null");



        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                    SellAdapter infoAdapter = new SellAdapter(response.body(), getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(infoAdapter);
                    infoAdapter.setOnItemClickListener(SellFragment.this);
                    infoAdapter.notifyDataSetChanged();
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerView);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        interfaces.productList("product", ID, "all").enqueue(listCallBack);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()>0 && address.getText().length()>0 && number.getText().length()>0 && email.getText().length()>0 && !(id.equals("-1"))){
                    Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                ((MainActivity)requireActivity()).change("pro");
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            System.out.println(t.getCause());
                            System.out.println(t.getMessage() + "cececece");
                        }
                    };
                    interfaces.point("point", ID,id, number.getText().toString()).enqueue(listCallBack);
                }else {
                    Toast.makeText(getContext(),"Boş bırakmayınız",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(String position) {
         id=position;
    }
}