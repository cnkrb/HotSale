package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.LoginActivity;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class ProfilFragment extends Fragment {

    View view;
    LinearLayout category,search,exit,info,favorite,product,share,sell;
    TextView order,point;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profil, container, false);

        category=view.findViewById(R.id.category);
        order=view.findViewById(R.id.order);
        point=view.findViewById(R.id.point);
        share=view.findViewById(R.id.share);
        search=view.findViewById(R.id.search);
        exit=view.findViewById(R.id.exit);
        sell=view.findViewById(R.id.sell);
        favorite=view.findViewById(R.id.favorite);
        info=view.findViewById(R.id.info);
        product=view.findViewById(R.id.product);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("o");
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("c");
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("t");
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Uygulamayı indir kayıt olurken referansa malimi yaz beraber puan kazanalım";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Uygulamayı Paylaş Puan Kazan");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Şununla Paylaş"));
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("p");
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("b");
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ID","null");
                editor.putBoolean("firstTime",false);
                editor.apply();

                FirebaseAuth.getInstance().signOut();


                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                ((MainActivity)requireActivity()).finish();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* FragmentManager manager = getFragmentManager();
                KuponFragment mydialog = new KuponFragment();
                mydialog.show(manager, "mydialog")*/;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("s");
            }
        });
        return  view;
    }
}