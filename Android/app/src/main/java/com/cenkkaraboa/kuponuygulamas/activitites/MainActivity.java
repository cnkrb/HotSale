package com.cenkkaraboa.kuponuygulamas.activitites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.Utils.Utils;
import com.cenkkaraboa.kuponuygulamas.adapters.CategoryAdapter;
import com.cenkkaraboa.kuponuygulamas.fragments.CategoryFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.DenemeFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.LikeFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.ProductDetailsFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.ProductFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.ProfilFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.SalesFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.SearchFragment;
import com.cenkkaraboa.kuponuygulamas.fragments.SellFragment;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.DefaultboolResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;
import static com.cenkkaraboa.kuponuygulamas.fragments.CategoryFragment.categoryAdapter;
import static com.cenkkaraboa.kuponuygulamas.fragments.LikeFragment.likeAdapter;
import static com.cenkkaraboa.kuponuygulamas.fragments.SalesFragment.salesAdapter;

public class MainActivity extends AppCompatActivity {

    TextView txt_head;
    ImageView profil, back, next;
    CardView search;
    String searchTextt;
    String deneme="asdad";
    String ID;
    boolean selectedCategory,selectedSearch;
    Boolean value = false;
    TextInputEditText searchText;
    String place = "zero";
    LinearLayout home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Utils.createLoginAPI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }/*else {

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        }*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ID = preferences.getString("ID", "null");

        txt_head = findViewById(R.id.txt_head);
        home = findViewById(R.id.home);
        searchText = findViewById(R.id.searchText);
        next = findViewById(R.id.next);
        profil = findViewById(R.id.profil);
        search = findViewById(R.id.search);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(place.equals("p")){

                    FragmentManager fm = getSupportFragmentManager();
                    ProductDetailsFragment fragment = (ProductDetailsFragment)fm.findFragmentById(R.id.activity_frame);
                    fragment.back();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(place.equals("p")){
                    FragmentManager fm = getSupportFragmentManager();
                    ProductDetailsFragment fragment = (ProductDetailsFragment)fm.findFragmentById(R.id.activity_frame);
                    fragment.next();

                }else if(place.toString().equals("k")){
                    closeKeyboard();

                    moveToFragment(new SearchFragment());
                    txt_head.setText("ARADIKLARIM");
                    profil.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    back.setVisibility(View.GONE);
                    place = "s";
                }else if(place.toString().equals("s")){
                    closeKeyboard();

                    txt_head.setText("ÜRÜNLERİM");
                    moveToFragment(new ProductDetailsFragment());
                    profil.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    place = "p";
                    back.setVisibility(View.VISIBLE);
                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                searchTextt = s.toString();
                if (place.toString().equals("k")) {
                    categoryAdapter.getFilter().filter(s.toString());

                } else if (place.toString().equals("s")) {
                    searchTextt = s.toString();
                    System.out.println(searchTextt);
                } else if (place.toString().equals("p")) {

                }else if (place.toString().equals("b")) {
                    likeAdapter.getFilter().filter(s.toString());
                }else if (place.toString().equals("t")) {
                    salesAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchText.setOnEditorActionListener(actionListener);
     /*   moveToFragment(new ProfilFragment());
        txt_head.setText("PROFİLİM");
        back.setVisibility(View.GONE);
        profil.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        search.setVisibility(View.INVISIBLE);
        searchText.setOnEditorActionListener(actionListener);*/

      /*      @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // If user press done key
                if(i == EditorInfo.IME_ACTION_DONE){
                    // Get the input method manager
                    Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.isSuccessful()) {
                                if(response.body().getResult()){
                                    FragmentManager fm = getSupportFragmentManager();
                                    SearchFragment fragment = (SearchFragment)fm.findFragmentById(R.id.searchFragment);
                                    fragment.load();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            System.out.println(t.getCause());
                            System.out.println(t.getMessage()+"cececece");
                        }
                    };
                    interfaces.searchDelete("search",ID,searchTextt).enqueue(listCallBack);

                    return true;
                }
                return false;
            }
        });
*/


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                moveToFragment(new ProfilFragment());
                txt_head.setText("PROFİLİM");
                back.setVisibility(View.GONE);
                profil.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
            }
        });


        selectedCategory = preferences.getBoolean("selectedCategory", false);
        selectedSearch = preferences.getBoolean("selectedSearch",false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!selectedCategory){
            moveToFragment(new CategoryFragment(true));
            txt_head.setText("KATEGORİLERİM");
            profil.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
            editor.putBoolean("selectedCategory",true);
            next.setVisibility(View.VISIBLE);
            place = "k";
            back.setVisibility(View.GONE);
            editor.apply();
        }else if(!selectedSearch){
            moveToFragment(new SearchFragment());
            txt_head.setText("ARADIKLARIM");
            profil.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
            editor.putBoolean("selectedSearch",true);
            editor.apply();
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            place = "s";
        }else {
            txt_head.setText("ÜRÜNLERİM");
            moveToFragment(new ProductDetailsFragment());
            profil.setVisibility(View.VISIBLE);
            search.setVisibility(View.INVISIBLE);
            next.setVisibility(View.GONE);
            place = "p";
            back.setVisibility(View.VISIBLE);
        }


    }


    public void hideBack() {
        back.setVisibility(View.GONE);
    }

    public void visiBack() {
        back.setVisibility(View.VISIBLE);
    }
    public void headText(String text) {
        txt_head.setText(text);
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public void change(String code) {
        closeKeyboard();
        home.setBackgroundResource(R.color.intro);

        switch (code) {

            case "o":
                moveToFragment(new SellFragment());
                txt_head.setText("Sipariş Oluştur");
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                next.setVisibility(View.GONE);
                home.setBackgroundResource(R.color.white);
                place = "o";
                back.setVisibility(View.GONE);
                break;

            case "c":
                moveToFragment(new CategoryFragment());
                txt_head.setText("KATEGORİLERİM");
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                place = "k";
                back.setVisibility(View.GONE);
                break;
            case "s":
                moveToFragment(new SearchFragment());
                txt_head.setText("ARADIKLARIM");
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                place = "s";
                break;
            case "p":
                txt_head.setText("ÜRÜNLERİM");
                moveToFragment(new ProductDetailsFragment());
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                next.setVisibility(View.GONE);
                place = "p";
                back.setVisibility(View.VISIBLE);
                break;
            case "b":
                txt_head.setText("BEĞENDİKLERİM");
                moveToFragment(new LikeFragment());
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                place = "b";
                back.setVisibility(View.GONE);
                break;

            case "t":
                txt_head.setText("Satın Aldıklarım");
                moveToFragment(new SalesFragment());
                profil.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                place = "t";
                back.setVisibility(View.GONE);
                break;

            case "pro":
                moveToFragment(new ProfilFragment());
                txt_head.setText("PROFİLİM");
                back.setVisibility(View.GONE);
                profil.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                search.setVisibility(View.INVISIBLE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + code);
        }
    }

    public void closeFragment() {
        getSupportFragmentManager().popBackStackImmediate();
        value = false;

        back.setVisibility(View.GONE);
        profil.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        search.setVisibility(View.VISIBLE);
    }

    TextView.OnEditorActionListener actionListener
            = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v,
                                      int actionId, KeyEvent event) {
            switch (actionId) {

                case EditorInfo.IME_ACTION_NEXT:

                    break;

                case EditorInfo.IME_ACTION_DONE:


                    if (place.equals("s")) {
                        if(searchTextt.toString().length()>0){
                            SearchFragment obj = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_frame);
                            obj.add(searchTextt);
                            searchText.setText(null);

                        }else {
                            Toast.makeText(getApplicationContext(),"Boş bırakmayınız",Toast.LENGTH_SHORT).show();

                        }

                    /*    Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getResult()) {
                                        SearchFragment obj = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_frame);
                                        obj.load();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                System.out.println(t.getCause());
                                System.out.println(t.getMessage() + "cececece");
                            }
                        };
                        interfaces.searchSave("search", ID, searchTextt).enqueue(listCallBack);*/
                    }

                    break;
            }
            return false;
        }
    };


    public void moveToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void moveToFragmentStack(Fragment fragment) {
        value = true;
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Çıkmak için tekrar dokunun", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1500);



    }
}