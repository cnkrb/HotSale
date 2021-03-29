package com.cenkkaraboa.kuponuygulamas.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.adapters.CouponAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.LikeAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SalesAdapter;
import com.cenkkaraboa.kuponuygulamas.models.Coupon;
import com.cenkkaraboa.kuponuygulamas.models.Like;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;

public class KuponFragment   extends DialogFragment implements CouponAdapter.OnItemClickListener {


    View view;
    TextView copy;
    String ID;
    ImageView exit;
    CardView CardError;
    List<Coupon> list;
    RecyclerView recyclerView;
    CouponAdapter couponAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setCancelable(false);
        view = inflater.inflate(R.layout.fragment_kupon, container, false);
        String strtext = getArguments().getString("id");

        //   copy = view.findViewById(R.id.copy);
        exit = view.findViewById(R.id.exit);
        CardError = view.findViewById(R.id.CardError);
        CardError.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView);
      /*  copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("kod", "deneme");
                clipboard.setPrimaryClip(clip);
                  Toast.makeText(getContext(),"Kopyalandı",Toast.LENGTH_SHORT).show();
            }
        });*/

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        ID=preferences.getString("ID","null");



        Callback<List<Coupon>> listCallBack = new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (response.isSuccessful()) {
                   list=response.body();
                   if(list.size()>0){
                       couponAdapter = new CouponAdapter(response.body(), getContext());
                       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                       recyclerView.setAdapter(couponAdapter);
                       couponAdapter.setOnItemClickListener(KuponFragment.this);
                       couponAdapter.notifyDataSetChanged();
                   }else {
                       CardError.setVisibility(View.VISIBLE);

                   }

                }
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {
            }
        };
        interfaces.coupon("coupon", ID, strtext).enqueue(listCallBack);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;

    }

    @Override
    public void onItemClick(Integer position, boolean value) {
        if(value){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl()));
            startActivity(browserIntent);
        }else{
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("kod", list.get(position).getCode());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(),"Kopyalandı",Toast.LENGTH_SHORT).show();
        }
    }
}

