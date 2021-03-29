package com.cenkkaraboa.kuponuygulamas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.fragments.KuponFragment;
import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.cenkkaraboa.kuponuygulamas.models.Product;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    public List<Product> itemList;
    private TextView[] dots;
    String [] layouts;
    public Context context;
    private OnItemClickListener mListener;
    public int count=0;
    Boolean value=false;
    private MyViewPagerAdapter myViewPagerAdapter;
    public interface OnItemClickListener {
        void onItemClick(String position,String value);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter(List<Product> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public ProductAdapter(List<Product> itemList, Context context,Boolean value) {
        this.itemList = itemList;
        this.context = context;
        this.value=value;
    }

    @Override
    public ProductAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (value) {
            return new ProductAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control, parent, false));

        }else{
            return new ProductAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));

        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ProductAdapter.Holder holder, int position) {

        //((MainActivity)context).headText(itemList.get(position).getName());

        holder.oneText.setText(itemList.get(position).getPriceOld());
           holder.twoText.setText("%"+itemList.get(position).getDiscountRate()+" İNDİRİM");
           holder.threeText.setText(itemList.get(position).getPrice());
        // ((MainActivity)context).change("pro");
        holder.two.setTextColor(context.getResources().getColor(R.color.text));
        holder.one.setTextColor(context.getResources().getColor(R.color.black));
        holder.deneme.setText(itemList.get(position).getBuyDescription());

           if(position % 2 ==0){
            holder.color.setBackgroundColor(context.getResources().getColor(R.color.bg_2));

        }else {
            holder.color.setBackgroundColor(context.getResources().getColor(R.color.bg_4));

        }
           holder.one.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   holder.one.setTextColor(context.getResources().getColor(R.color.text));
                   holder.two.setTextColor(context.getResources().getColor(R.color.black));
                   holder.deneme.setText(itemList.get(position).getDescription());
               }
           });


        holder.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.two.setTextColor(context.getResources().getColor(R.color.text));
                holder.one.setTextColor(context.getResources().getColor(R.color.black));
                holder.deneme.setText(itemList.get(position).getBuyDescription());
            }
        });

           /* if(itemList.get(position).getImage().toString().length()>0){
                Picasso.with(context)
                        .load(itemList.get(position).getImage())
                        .into(holder.image);
            }*/


        layouts=itemList.get(position).getImage().split(",");
        System.out.println(layouts.length+"asdjkaskjdsadasdasd");

    /*    layouts = new int[]{
                R.drawable.boarding_1,
                R.drawable.boarding_2,
                R.drawable.boarding_3};*/
        addBottomDots(0,holder,layouts.length);

        myViewPagerAdapter = new MyViewPagerAdapter(layouts);
        holder.viewPager.setAdapter(myViewPagerAdapter);
        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // addBottomDots(position,holder);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        holder.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] sayı=itemList.get(position).getImage().split(",");

                if(holder.viewPager.getCurrentItem() != 0){
                    System.out.println(holder.viewPager.getCurrentItem()+"asdfasdasdsadassdasdsad");
                    addBottomDots(holder.viewPager.getCurrentItem()-1,holder,sayı.length);
                    holder.viewPager.setCurrentItem((holder.viewPager.getCurrentItem())-1);
                    System.out.println(holder.viewPager.getCurrentItem()+"asdfasdasdsadassdasdsad");
                }

            }
        });


        holder.imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] sayı=itemList.get(position).getImage().split(",");

                if(holder.viewPager.getCurrentItem() !=sayı.length-1){
                    System.out.println(holder.viewPager.getCurrentItem()+"asdfasdasdsadassdasdsad");
                    addBottomDots(holder.viewPager.getCurrentItem()+1,holder,sayı.length);
                    holder.viewPager.setCurrentItem((holder.viewPager.getCurrentItem())+1);
                    System.out.println(holder.viewPager.getCurrentItem()+"asdfasdasdsadassdasdsad");
                }

            }
        });

    }

    public List<Product> getItems() {
        return itemList;
    }

    public void setItems(List<Product> items) {
        this.itemList = items;
    }

    private void addBottomDots(int currentPage,ProductAdapter.Holder holder,int a) {
        dots = new TextView[a];

        int[] colorsActive = context.getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = context.getResources().getIntArray(R.array.array_dot_inactive);

        holder.dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            holder.dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    @Override
    public int getItemCount() {
        return itemList.size();

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
          //  addBottomDots(position);
            System.out.println("aşdaskdklşsadasd");
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };



    public class Holder extends RecyclerView.ViewHolder {


        TextView one,two,three,oneText,twoText,threeText,deneme;
        FrameLayout imageBack,imageNext;
        TextView sales;
        MaterialCardView fav,share;

        ImageView image;
        ViewPager viewPager;
        LinearLayout dotsLayout,color;
        MaterialCardView cardView;
        LinearLayout home;
        TextView go,close;
        public Holder(final View itemView) {
            super(itemView);
            sales =  itemView.findViewById(R.id.sales);


            if (value) {
                go =  itemView.findViewById(R.id.go);
                close =  itemView.findViewById(R.id.close);

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick("1","go");
                            }
                        }
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick("1","close");
                            }
                        }
                    }
                });
            }

            imageBack =  itemView.findViewById(R.id.imageBack);
            imageNext =  itemView.findViewById(R.id.imageNext);
            color =  itemView.findViewById(R.id.color);
            fav =  itemView.findViewById(R.id.fav);
            share =  itemView.findViewById(R.id.share);

            one =  itemView.findViewById(R.id.one);
            two =  itemView.findViewById(R.id.two);
            three =  itemView.findViewById(R.id.three);
            oneText =  itemView.findViewById(R.id.oneText);
            twoText =  itemView.findViewById(R.id.twoText);
            threeText =  itemView.findViewById(R.id.threeText);
            deneme =  itemView.findViewById(R.id.deneme);


            dotsLayout = (LinearLayout) itemView.findViewById(R.id.layoutDots);
            viewPager   = (ViewPager) itemView.findViewById(R.id.viewPager);





            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemList.get(position).getId(),"like");
                        }
                    }
                }
            });

            three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(String.valueOf(position),"kupon");
                        }
                    }

                }
            });

            sales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemList.get(position).getId(),"sepet");
                        }
                    }
                }
            });


        }
    }








    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private String [] IMAGES;

        public MyViewPagerAdapter(String [] IMAGES){
            this.IMAGES=IMAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View imageLayout = layoutInflater.inflate(R.layout.viewpager, container, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);
            Picasso.with(context)
                    .load(IMAGES[position])
                    .into(imageView);
            container.addView(imageLayout, 0);

            return imageLayout;
        }

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}