package com.cenkkaraboa.kuponuygulamas.interfaces;

import com.cenkkaraboa.kuponuygulamas.models.Category;
import com.cenkkaraboa.kuponuygulamas.models.Coupon;
import com.cenkkaraboa.kuponuygulamas.models.DefaultboolResponse;
import com.cenkkaraboa.kuponuygulamas.models.Like;
import com.cenkkaraboa.kuponuygulamas.models.Product;
import com.cenkkaraboa.kuponuygulamas.models.Register;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Search;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Interfaces {

    @FormUrlEncoded
    @POST("/")
    Call<Register> registerAndLogin(@Field("page") String page,@Field("email") String email,@Field("gender") String gender,
                            @Field("birthday") String birthday,@Field("emailRef") String emailRef,@Field("password") String password,
                            @Field("lang") String lang);

    @FormUrlEncoded
    @POST("/")
    Call<Register> registerSosyal(@Field("page") String page,@Field("email") String email,@Field("gender") String gender,
                                    @Field("birthday") String birthday,@Field("emailRef") String emailRef,@Field("password") String password,
                                    @Field("lang") String lang,@Field("firstname") String firstname,@Field("lastname") String lastname,@Field("loginID") String loginID);

    @FormUrlEncoded
    @POST("/")
    Call<Register> login(@Field("page") String page,@Field("email") String email,@Field("password") String password);


    @FormUrlEncoded
    @POST("/")
    Call<Register> loginSosyal(@Field("page") String page,@Field("loginID") String loginID);


    //http://urun.phpscript.info/?page=category&guid=7916E683-65B4-441E-97D5-62980B&list=

    @FormUrlEncoded
    @POST("/")
    Call<List<Category>> categoryList(@Field("page") String page,@Field("guid") String guid,@Field("list") String list);



    //http://urun.phpscript.info/?page=category&guid=7916E683-65B4-441E-97D5-62980B&category=123

    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> categorySave(@Field("page") String page,@Field("guid") String guid,@Field("category") String category);


    //http://urun.phpscript.info/?page=category&guid=7916E683-65B4-441E-97D5-62980B&ID=123

    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> categoryDelete(@Field("page") String page,@Field("guid") String guid,@Field("ID") String ID);


    //http://urun.phpscript.info/?page=product&guid=7916E683-65B4-441E-97D5-62980B&list=

    @FormUrlEncoded
    @POST("/")
    Call<List<Product>> productList(@Field("page") String page, @Field("guid") String guid, @Field("list") String list);


    @FormUrlEncoded
    @POST("/")
    Call<List<Search>> searchtList(@Field("page") String page, @Field("guid") String guid, @Field("list") String list);


    @FormUrlEncoded
    @POST("/")
    Call<List<Like>> likeList(@Field("page") String page, @Field("guid") String guid, @Field("list") String list);



    @FormUrlEncoded
    @POST("/")
    Call<List<Like>> salesList(@Field("page") String page, @Field("guid") String guid, @Field("list") String list);


    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> likeDelete(@Field("page") String page, @Field("guid") String guid, @Field("ID") String ID);


    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> likeSave(@Field("page") String page, @Field("guid") String guid, @Field("productid") String productid);

    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> salesSave(@Field("page") String page, @Field("guid") String guid, @Field("productid") String productid);


    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> searchDelete(@Field("page") String page, @Field("guid") String guid, @Field("ID") String ID);


    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> searchSave(@Field("page") String page, @Field("guid") String guid, @Field("search") String search);

    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> privateLike(@Field("page") String page, @Field("guid") String guid, @Field("ID") String ID, @Field("like") String like);

    @FormUrlEncoded
    @POST("/")
    Call<List<Coupon>> coupon(@Field("page") String page, @Field("guid") String guid, @Field("ID") String ID);

    @FormUrlEncoded
    @POST("/")
    Call<DefaultResponse> point(@Field("page") String page, @Field("guid") String guid, @Field("ID") String ID, @Field("phone") String phone);


    @Multipart
    @POST("/")
    Call<DefaultResponse> salesImage(@Part("page") String page, @Part("guid") String guid, @Part("ID") String ID,@Part MultipartBody.Part image);
}
