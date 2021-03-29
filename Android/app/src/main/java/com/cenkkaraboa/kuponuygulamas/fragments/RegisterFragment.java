package com.cenkkaraboa.kuponuygulamas.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.LoginActivity;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.models.Register;
import com.google.android.material.card.MaterialCardView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;
import static com.google.gson.internal.bind.TypeAdapters.UUID;


public class RegisterFragment extends Fragment {
    MaterialSpinner spinner;
    MaterialCardView save;
    View view;
    String date="";
    int pos=-1;

    EditText ref,email,password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);

        spinner = (MaterialSpinner)view.findViewById(R.id.spinner);
        save = view.findViewById(R.id.save);
        ref = view.findViewById(R.id.ref);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        TextView textView=view.findViewById(R.id.login);
        TextView dater=view.findViewById(R.id.date);
        dater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog   picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dater.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                date=String.valueOf(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        String[] price =new String[3];
        price[0]="Cinsiyet seçmek için tıklayınız.";
        price[1]="Kadın";
        price[2]="Erkek";
        spinner.setItems(price);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(price[position].equals("Cinsiyet seçmek için tıklayınız.")){

                }else {
                    //   value=priceList.get(position-1).getId();
                }
                pos=position;
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((LoginActivity)requireActivity()).moveToFragment(new LoginFragment());
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.toString().length() != 0 && password.toString().length() != 0 && date.toString().length() != 0 && pos>0){

                    Callback<Register> listCallBack = new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(getContext(),response.body().getID(),Toast.LENGTH_SHORT).show();
                                if (response.body().isResult()){
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("firstTime",true);
                                    editor.putString("ID","");
                                    System.out.println(response.body().getID()+"asdasdsadasdasdasd");

                                    editor.putString("ID",response.body().getID());
                                    editor.putInt("search",Integer.parseInt(response.body().getSearch()));
                                    editor.putInt("category",Integer.parseInt(response.body().getCategory()));
                                    editor.apply();



                                    Intent intent=new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    ((LoginActivity)requireActivity()).finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            System.out.println(t.getCause());
                            System.out.println(t.getMessage()+"cececece");
                        }
                    };
                    interfaces.registerAndLogin("register",email.getText().toString(),price[pos].toString(),date,ref.getText().toString(),password.getText().toString(),"tr").enqueue(listCallBack);

                }else {
                    if(email.getText().toString().length()==0){
                        Toast.makeText(getContext(),"Email Adresi Giriniz",Toast.LENGTH_SHORT).show();

                    }else if(password.getText().toString().length()==0){
                        Toast.makeText(getContext(),"Şifre Giriniz",Toast.LENGTH_SHORT).show();

                    }else if(date.toString().length()==0){
                        Toast.makeText(getContext(),"Doğum Tarihi Seçiniz",Toast.LENGTH_SHORT).show();
                    }else if(pos<0){
                        Toast.makeText(getContext(),"Cinsiyet Seçiniz",Toast.LENGTH_SHORT).show();
                    }
                }
         }
        });

        return  view;
    }
}