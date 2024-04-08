package com.example.ass_adr_api.services;

import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.Models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static String BASE_URL = "http://192.168.0.102:3000/api/";//url cua api
// đăng kí
    @Multipart
    @POST("register-send-email")
    Call<Response<User>> register(@Part("username") RequestBody username,
                                  @Part("password") RequestBody password,
                                  @Part("email") RequestBody email,
                                  @Part("name") RequestBody name,
                                  @Part MultipartBody.Part avatar);
    @POST("login")
    Call<Response<User>> login(@Body User user);
    @GET("get-list-user")
    Call<Response<ArrayList<User>>> getListUser();
    @GET("get-favorite-products")
    Call<Response<ArrayList<Product>>> getFavoriteProducts(@Query("id") String productId);
    @GET("get-user-info")
    Call<Response<User>> getUserInfo(@Query("id") String userId);
    @PUT("update-user-by-id/{id}")
    Call<Response<User>> editUserApi(@Path("id") String id, @Body User user);

    @GET("get-list-categories")
    Call<Response<ArrayList<Categorie>>> getListCategories();
    @GET("get-list-product")
    Call<Response<ArrayList<Product>>> getListProduct();
    @PUT("update-product-by-id/{id}")
    Call<Response<Product>> updateProductApi(@Path("id") String id, @Body Product product);
}
