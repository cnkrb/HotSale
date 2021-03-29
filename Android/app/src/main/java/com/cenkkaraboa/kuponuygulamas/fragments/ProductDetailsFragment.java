package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.CardStackCallback;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.adapters.ProductAdapter;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Product;
import com.google.android.material.card.MaterialCardView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class ProductDetailsFragment extends Fragment implements  ProductAdapter.OnItemClickListener , CardStackListener {
    List<Product> list;
    MaterialCardView fav,share;
    TextView sales;
    ImageView like;
    RecyclerView recyclerView;
    View view;
    String ID;
    private CardStackLayoutManager manager;
    CardStackView cardStackView;
    ProductAdapter adapter;
    RelativeLayout altbar;
    TextView go,close;
    LinearLayout control;
    Boolean isFinish=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_details, container, false);
        cardStackView = view.findViewById(R.id.card_stack_view);
        fav = view.findViewById(R.id.fav);
        share = view.findViewById(R.id.share);
        like = view.findViewById(R.id.like);
        sales = view.findViewById(R.id.sales);
        altbar = view.findViewById(R.id.altbar);
        go = view.findViewById(R.id.go);
        close = view.findViewById(R.id.close);
        control = view.findViewById(R.id.control);

        manager = new CardStackLayoutManager(getContext(),this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ID = preferences.getString("ID", "null");
        System.out.println(ID+" ASDASDASDASDA");


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {

                            control.setVisibility(View.GONE);
                            cardStackView.setVisibility(View.VISIBLE);
                            altbar.setVisibility(View.VISIBLE);
                            isFinish=true;
                            ((MainActivity)requireActivity()).visiBack();
                            if (response.body().size() > 0) {
                                list=response.body();
                                manager.setStackFrom(StackFrom.None);
                                manager.setVisibleCount(3);
                                manager.setTranslationInterval(8.0f);
                                manager.setScaleInterval(0.95f);
                                manager.setSwipeThreshold(0.3f);
                                manager.setMaxDegree(20.0f);
                                manager.setDirections(Direction.HORIZONTAL);
                                manager.setCanScrollHorizontal(true);
                                manager.setCanScrollVertical(true);
                                manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                                manager.setOverlayInterpolator(new LinearInterpolator());
                                adapter = new ProductAdapter(list,getContext());
                                cardStackView.setLayoutManager(manager);
                                cardStackView.setAdapter(adapter);
                                adapter.setOnItemClickListener(ProductDetailsFragment.this);
                                cardStackView.setItemAnimator(new DefaultItemAnimator());
                                adapter.notifyDataSetChanged();
                                if(list.get(manager.getTopPosition()).getIstLike().equals("1")){
                                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
                                    like.setTag("fav");
                                }else{
                                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                    like.setTag("notfav");
                                }
                                ((MainActivity)requireActivity()).headText(list.get(manager.getTopPosition()).getName());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                };
                interfaces.productList("product", ID, "all").enqueue(listCallBack);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).change("pro");

            }
        });


        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(manager.getTopPosition()==list.size()){

                }else{

                    Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.isSuccessful()) {
                               // Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(manager.getTopPosition()).getRefUrl()));
                                startActivity(browserIntent);
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            System.out.println(t.getCause());
                            System.out.println(t.getMessage() + "cececece");
                        }
                    };
                    interfaces.salesSave("sales", ID, list.get(manager.getTopPosition()).getId()).enqueue(listCallBack);
                }


            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(manager.getTopPosition()==list.size()){

                }else{
                    if(like.getTag().equals("fav")){
                        Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (response.isSuccessful()) {
                                    like.setTag("fava");
                                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                    Product product=new Product();
                                    product=list.get(manager.getTopPosition());
                                    product.setIstLike("0");
                                    list.set(manager.getTopPosition(),product);
                                    adapter.notifyItemChanged(manager.getTopPosition());
                                }
                            }
                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                System.out.println(t.getCause());
                                System.out.println(t.getMessage()+"cececece");
                            }
                        };
                        interfaces.likeDelete("like",ID,list.get(manager.getTopPosition()).getId()).enqueue(listCallBack);
                    }else {
                        Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (response.isSuccessful()) {
                                    like.setTag("fav");
                                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
                                    Product product=new Product();
                                    product=list.get(manager.getTopPosition());
                                    product.setIstLike("1");
                                    list.set(manager.getTopPosition(),product);
                                    adapter.notifyItemChanged(manager.getTopPosition());

                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                System.out.println(t.getCause());
                                System.out.println(t.getMessage() + "cececece");
                            }
                        };
                        interfaces.likeSave("like", ID, list.get(manager.getTopPosition()).getId()).enqueue(listCallBack);
                    }

                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(manager.getTopPosition()==list.size()){

                }else{
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = list.get(manager.getTopPosition()).getName()+" Ürünü"+list.get(manager.getTopPosition()).getDiscountRate()+" İndirimde Uygulamayı indir  hemen satın al";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Uygulamayı İndir İndirimi kap");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Şununla Paylaş"));
                }


            }
        });

        load();
        return view;
    }

    public void load(){
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().size()+"asdasdasd");
                    if (response.body().size() > 0) {
                        list=response.body();
                      /*  Toast.makeText(getContext(),response.body().size(),Toast.LENGTH_SHORT).show();
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                        ProductAdapter categoryAdapter = new ProductAdapter(response.body(), getContext());
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setAdapter(categoryAdapter);
                        categoryAdapter.setOnItemClickListener(ProductDetailsFragment.this);
                        categoryAdapter.notifyDataSetChanged();

                        SnapHelper snapHelper = new PagerSnapHelper();
                        snapHelper.attachToRecyclerView(recyclerView);*/
                        manager.setStackFrom(StackFrom.None);
                        manager.setVisibleCount(3);
                        manager.setTranslationInterval(8.0f);
                        manager.setScaleInterval(0.95f);
                        manager.setSwipeThreshold(0.3f);
                        manager.setMaxDegree(20.0f);
                        manager.setDirections(Direction.HORIZONTAL);
                        manager.setCanScrollHorizontal(true);
                        manager.setCanScrollVertical(true);
                        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                        manager.setOverlayInterpolator(new LinearInterpolator());
                        adapter = new ProductAdapter(list,getContext());
                        cardStackView.setLayoutManager(manager);
                        cardStackView.setAdapter(adapter);
                        adapter.setOnItemClickListener(ProductDetailsFragment.this);
                        cardStackView.setItemAnimator(new DefaultItemAnimator());

                        if(Integer.parseInt(list.get(manager.getTopPosition()).getUserLike().toString())>=1){
                            like.setImageResource(R.drawable.ic_baseline_favorite_24);
                            like.setTag("fav");
                        }else{
                            like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            like.setTag("notfav");
                        }
                        // ((MainActivity)requireActivity()).headText(list.get(manager.getTopPosition()).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        interfaces.productList("product", ID, "").enqueue(listCallBack);

    }


    public  void like(String id,String like){
        Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage() + "cececece");
            }
        };
        interfaces.privateLike("privateLike", ID,id, like).enqueue(listCallBack);
    }

    public void next(){
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new AccelerateInterpolator())
                .build();
        manager.setRewindAnimationSetting(setting);
        cardStackView.swipe();

    }

    public void back(){
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new DecelerateInterpolator())
                .build();
        manager.setRewindAnimationSetting(setting);
        cardStackView.rewind();

        if(list.get(manager.getTopPosition()).getIstLike().equals("1")){
            like.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        ((MainActivity)requireActivity()).headText(list.get(manager.getTopPosition()).getName());
    }

    @Override
    public void onItemClick(String position, String value) {
        switch (value) {
            case "kupon":
                System.out.println("geliyorrr");

                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(Integer.parseInt(position)).getId().toString());

                FragmentManager managerr = getFragmentManager();
                KuponFragment mydialog = new KuponFragment();
                mydialog.setArguments(bundle);
                mydialog.show(managerr, "mydialog");
                break;
            case "like": {

                break;
            }
            case "sepet": {
                Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        System.out.println(t.getCause());
                        System.out.println(t.getMessage() + "cececece");
                    }
                };
                interfaces.salesSave("sales", ID, position).enqueue(listCallBack);
                break;
            }
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if(manager.getTopPosition()==list.size()){

            if(isFinish){
                ((MainActivity)requireActivity()).headText("Gösterek ürün kalmadı");
                altbar.setVisibility(View.GONE);
                ((MainActivity)requireActivity()).hideBack();
            }else {
                control.setVisibility(View.VISIBLE);
                cardStackView.setVisibility(View.GONE);
                altbar.setVisibility(View.GONE);
                ((MainActivity)requireActivity()).headText("Daha fazla ürün ?");
                ((MainActivity)requireActivity()).hideBack();
            }

       /*     AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Daha fazla ürün görmek ister misin ?");
            builder.setNegativeButton("Hayır", null);
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();*/
        }else{

            if(Integer.parseInt(list.get(manager.getTopPosition()).getUserLike().toString())>=1){
                like.setImageResource(R.drawable.ic_baseline_favorite_24);
                like.setTag("fav");
            }else{
                like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                like.setTag("notfav");
            }
            ((MainActivity)requireActivity()).headText(list.get(manager.getTopPosition()).getName());


            if (direction == Direction.Right){
                like(list.get(manager.getTopPosition()).getId(),list.get(manager.getTopPosition()).getIstLike());
            }

            if (direction == Direction.Left){
               // like(list.get(manager.getTopPosition()).getId(),"0");
            }
        }


        if (manager.getTopPosition() == adapter.getItemCount() - 5) {

            paginate();
        }






    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    private void paginate() {
        List<Product> old = adapter.getItems();
        List<Product> baru = new ArrayList<>(list);
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }
}